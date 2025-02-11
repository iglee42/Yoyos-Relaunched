package fr.iglee42.yoyos.compat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.iglee42.yoyos.Config;
import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.Interaction;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.resourcepack.InMemoryPack;
import fr.iglee42.yoyos.resourcepack.PackType;
import fr.iglee42.yoyos.resourcepack.YoyosPackFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;

import javax.annotation.Nullable;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YoyoPluginHelper {

    public static List<IYoyoPlugin> PLUGINS = new ArrayList<>();
    public static final Map<String,String> YOYO_BY_PLUGIN = new HashMap<>();
    public static final List<YoyoTier> YOYO_TIERS = new ArrayList<>();

    public static final Map<String,String> YOYO_BY_MODS = new HashMap<>();
    public static final Map<String,String> CUSTOM_CORD = new HashMap<>();
    public static final Map<String,String> CUSTOM_ITEM = new HashMap<>();
    public static final Map<String, Constructor<? extends YoyoItem>> CUSTOM_CONSTRUCTOR = new HashMap<>();


    private static final List<YoyoTier> tempsYoyo = new ArrayList<>();

    public void registerYoyo(String name, double weight, double length, int duration, double damage, Tier tier,IYoyoPlugin plugin){
        if (tempsYoyo.stream().anyMatch(t->t.getName().equalsIgnoreCase(name))) return;
        tempsYoyo.add(new YoyoTier(name, weight, length, duration, damage, tier));
        YOYO_BY_PLUGIN.put( name,plugin.modId());
        YOYO_BY_MODS.put( name,plugin.modId());
    }

    public void setCustomCord(String name, String cord){
        CUSTOM_CORD.put(name,cord);
    }

    public void setCustomItem(String name, String item){
        CUSTOM_ITEM.put(name,item);
    }
    public void setCustomConstructor(String name,@Nullable Constructor<? extends YoyoItem> constructor){
        if (constructor == null) return;
        CUSTOM_CONSTRUCTOR.put(name,constructor);
    }

    public void init() throws IOException {
        if (Config.getConfigFile().exists()) {
            for (YoyoTier t : tempsYoyo) {
                JsonObject config = new Gson().fromJson(new FileReader(Config.getConfigFile()),JsonObject.class);
                if (config.has(t.getName().toLowerCase())){
                   YoyoTier newTier = YoyoTier.fromJson(config.get(t.getName().toLowerCase()).getAsJsonObject(), t);
                   YOYO_TIERS.add(newTier);
                }
                else {
                    YOYO_TIERS.add(t);
                    config.add(t.getName().toLowerCase(),t.toJson());
                    FileWriter writer = new FileWriter(Config.getConfigFile());
                    writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(config));
                    writer.close();
                }
            }
        }


    }

    public static void setPlugins(List<IYoyoPlugin> PLUGINS) {
        YoyoPluginHelper.PLUGINS = PLUGINS;
    }

    public static void registerItem(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.ITEM)) return;
       YOYO_TIERS.forEach(t->{
           event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,t.getName().toLowerCase() + "_yoyo"),
                   ()->{
               if (CUSTOM_CONSTRUCTOR.containsKey(t.getName().toLowerCase())){
                   try {
                       return CUSTOM_CONSTRUCTOR.get(t.getName().toLowerCase()).newInstance(t).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock);
                   } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                       return new YoyoItem(t).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock);
                   }
               } else {
                   return new YoyoItem(t).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock);
               }
            });
       });
        PLUGINS.forEach(p->p.registerItems(event));
    }

}
