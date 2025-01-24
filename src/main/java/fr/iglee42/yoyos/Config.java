package fr.iglee42.yoyos;

import fr.iglee42.yoyos.common.init.YoyosTiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Config {


    private static File configFile;

    public static void init() throws IOException, IllegalAccessException {
        new File(FMLPaths.CONFIGDIR.get().toFile(),"yoyos").mkdirs();
        configFile = new File(FMLPaths.CONFIGDIR.get().toFile(),"yoyos/yoyos-common.json");
        YoyosTiers.init();
    }

    public static File getConfigFile() {
        return configFile;
    }
}
