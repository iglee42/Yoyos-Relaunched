package fr.iglee42.yoyos;

import fr.iglee42.yoyos.common.YoyosTiers;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.IOException;


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
