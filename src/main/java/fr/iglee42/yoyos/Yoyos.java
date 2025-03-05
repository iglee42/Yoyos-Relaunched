package fr.iglee42.yoyos;

import com.mojang.logging.LogUtils;
import fr.iglee42.yoyos.client.YoyoRenderer;
import fr.iglee42.yoyos.client.YoyosKeybindings;
import fr.iglee42.yoyos.common.init.YoyosEnchantments;
import fr.iglee42.yoyos.common.init.YoyosEntities;
import fr.iglee42.yoyos.common.init.YoyosItems;
import fr.iglee42.yoyos.common.init.YoyosSounds;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.network.YoyosNetwork;
import fr.iglee42.yoyos.resourcepack.PackType;
import fr.iglee42.yoyos.resourcepack.PathConstant;
import fr.iglee42.yoyos.resourcepack.YoyosPackFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.*;

@Mod(Yoyos.MODID)
public class Yoyos {

    public static final String MODID = "yoyos";
    public static final Logger LOGGER = LogUtils.getLogger();
    protected static YoyoPluginHelper pluginHelper;

    public Yoyos() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        try {
            Config.init();
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        registerPlugins(modEventBus);

        modEventBus.addListener(YoyosItems::registerItem);
        YoyosItems.TABS.register(modEventBus);
        YoyosEnchantments.ENCHANTMENTS.register(modEventBus);
        YoyosEntities.ENTITY_TYPES.register(modEventBus);
        YoyosSounds.SOUNDS.register(modEventBus);

        PathConstant.init();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerEvent);


        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(this::clientSetup);
            MinecraftForge.EVENT_BUS.addListener(YoyosKeybindings::handleEventInput);
        }

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        try {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                Minecraft.getInstance().getResourcePackRepository().addPackFinder(new YoyosPackFinder(PackType.RESOURCE));
            }
        } catch (Exception ignored) {
            throw new RuntimeException(ignored);
        }

    }

    private <T extends IYoyoPlugin> void registerPlugins(IEventBus bus) {
        pluginHelper = new YoyoPluginHelper();
        Type annotationType = Type.getType(YoyoPlugin.class);
        Set<String> pluginClassNames = getClassesWithAnnotation(annotationType);
        List<T> loadedPlugins = new ArrayList<>();
        for (String className : pluginClassNames) {
            try {
                Class<?> asmClass = Class.forName(className);
                Class<T> asmInstanceClass = (Class<T>) asmClass.asSubclass(IYoyoPlugin.class);
                T instance = asmInstanceClass.getDeclaredConstructor().newInstance();
                if (instance.shouldLoad())loadedPlugins.add(instance);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }

        }

        loadedPlugins.forEach(p->{
            p.init(pluginHelper);
            p.registerYoyos(pluginHelper);
        });

        try {
            pluginHelper.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        pluginHelper.setPlugins((List<IYoyoPlugin>) loadedPlugins);
    }

    private static @NotNull Set<String> getClassesWithAnnotation(Type annotationType) {
        List<ModFileScanData> allScanData = ModList.get().getAllScanData();
        Set<String> pluginClassNames = new LinkedHashSet<>();
        for (ModFileScanData scanData : allScanData) {
            Iterable<ModFileScanData.AnnotationData> annotations = scanData.getAnnotations();
            for (ModFileScanData.AnnotationData a : annotations) {
                if (Objects.equals(a.annotationType(), annotationType)) {
                    String memberName = a.memberName();
                    pluginClassNames.add(memberName);
                }
            }
        }
        return pluginClassNames;
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        YoyosNetwork.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        EntityRenderers.register(YoyosEntities.YOYO.get(), YoyoRenderer::new);
        pluginHelper.plugins.forEach(p->p.clientSetup(event));
    }

    private void registerEvent(RegisterEvent event){
       if (event.getRegistryKey().equals(Registries.ITEM))
            pluginHelper.registerItems(event);
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != YoyosItems.TAB.getKey()) return;
        ForgeRegistries.ITEMS.getKeys().stream().filter(rs->rs.getNamespace().equals(MODID)).forEach(rs->{
            event.accept(ForgeRegistries.ITEMS.getDelegateOrThrow(rs));
        });
    }

    public static YoyoPluginHelper getPluginHelper() {
        return pluginHelper;
    }
}
