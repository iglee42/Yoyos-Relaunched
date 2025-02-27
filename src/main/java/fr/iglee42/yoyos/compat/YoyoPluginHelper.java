package fr.iglee42.yoyos.compat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.iglee42.yoyos.Config;
import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.Interaction;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.common.api.BlockInteraction;
import fr.iglee42.yoyos.common.api.EntityInteraction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class YoyoPluginHelper {

    public List<IYoyoPlugin> plugins = new ArrayList<>();
    public final List<YoyoTier> YOYO_TIERS = new ArrayList<>();
    private final List<YoyoTier> tempsYoyo = new ArrayList<>();

    public void registerYoyo(YoyoTier yoyoTier){
        if (tempsYoyo.stream().anyMatch(y->y.getName().equals(yoyoTier.getName()))) return;
        tempsYoyo.add(yoyoTier);
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

    public void setPlugins(List<IYoyoPlugin> plugins) {
        this.plugins = plugins;
    }

    public void registerItems(RegisterEvent event) {
        YOYO_TIERS.forEach(t -> {
            event.register(Registries.ITEM, new ResourceLocation(Yoyos.MODID, t.getName().toLowerCase() + "_yoyo"), () -> {
                //item = item.addBlockInteraction(t.getBlockInteractions().toArray(new BlockInteraction[]{}));
                //item = item.addEntityInteraction(t.getEntityInteractions().toArray(new EntityInteraction[]{}));
                if (t.getCustomConstructor() != null){

                    try {
                        return t.getCustomConstructor().newInstance(t).addEntityInteraction(t.getEntityInteractions().toArray(new EntityInteraction[]{})).addBlockInteraction(t.getBlockInteractions().toArray(new BlockInteraction[]{}));
                    } catch (InstantiationException | InvocationTargetException | IllegalAccessException ignored) {
                        return new YoyoItem(t).addEntityInteraction(t.getEntityInteractions().toArray(new EntityInteraction[]{})).addBlockInteraction(t.getBlockInteractions().toArray(new BlockInteraction[]{}));
                    }
                } else {
                  return new YoyoItem(t).addEntityInteraction(t.getEntityInteractions().toArray(new EntityInteraction[]{})).addBlockInteraction(t.getBlockInteractions().toArray(new BlockInteraction[]{}));

                }
            });
        });
        plugins.forEach(p -> p.registerItems(event));
    }


}
