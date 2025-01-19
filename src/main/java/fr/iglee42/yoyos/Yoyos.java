package fr.iglee42.yoyos;

import com.mojang.logging.LogUtils;
import fr.iglee42.yoyos.client.YoyoRenderer;
import fr.iglee42.yoyos.client.YoyosKeybindings;
import fr.iglee42.yoyos.common.init.YoyosEnchantments;
import fr.iglee42.yoyos.common.init.YoyosEntities;
import fr.iglee42.yoyos.common.init.YoyosItems;
import fr.iglee42.yoyos.network.YoyosNetwork;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(Yoyos.MODID)
public class Yoyos {

    public static final String MODID = "yoyos";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Yoyos() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        modEventBus.addListener(YoyosItems::registerItem);
        YoyosItems.TABS.register(modEventBus);
        YoyosEnchantments.ENCHANTMENTS.register(modEventBus);
        YoyosEntities.ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(YoyosKeybindings::handleEventInput);

        modEventBus.addListener(this::addCreative);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        YoyosNetwork.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        EntityRenderers.register(YoyosEntities.YOYO.get(), YoyoRenderer::new);
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != YoyosItems.TAB.getKey()) return;
        ForgeRegistries.ITEMS.getKeys().stream().filter(rs->rs.getNamespace().equals(MODID)).forEach(rs->{
            event.accept(ForgeRegistries.ITEMS.getDelegateOrThrow(rs));
        });
    }
}
