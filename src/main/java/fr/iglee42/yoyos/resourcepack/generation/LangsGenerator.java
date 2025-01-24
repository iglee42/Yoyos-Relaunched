package fr.iglee42.yoyos.resourcepack.generation;

import fr.iglee42.igleelib.api.utils.ModsUtils;
import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.resourcepack.PathConstant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static fr.iglee42.yoyos.Yoyos.MODID;

public class LangsGenerator {

    private static Map<String,String> langs = new HashMap<>();
    public static void generate() {

        YoyoPluginHelper.YOYO_TIERS.forEach(t->{
            langs.put("item."+MODID+"."+t.getName().toLowerCase()+"_yoyo",ModsUtils.getUpperName(t.getName().toLowerCase() + "_yoyo","_"));
        });

        try {
            FileWriter writer = new FileWriter(new File(PathConstant.LANGS_PATH.toFile(), "en_us.json"));
            writer.write("{\n");
            AtomicInteger index = new AtomicInteger(-1);
            langs.forEach((key,translation) -> {
                try {
                    index.getAndIncrement();
                    writer.write("  \"" + key + "\": \"" + translation + "\"" + (index.get() != langs.size() - 1? ",":"") + "\n");

                } catch (IOException e) {
                    Yoyos.LOGGER.error("An error was detected when langs generating",e);
                }
            });
            writer.write("}");
            writer.close();
        } catch (Exception exception){
            Yoyos.LOGGER.error("An error was detected when langs generating",exception);
        }
    }

}
