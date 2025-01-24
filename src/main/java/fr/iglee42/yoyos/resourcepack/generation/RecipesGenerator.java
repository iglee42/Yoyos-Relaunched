package fr.iglee42.yoyos.resourcepack.generation;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.resourcepack.PathConstant;

import java.io.File;
import java.io.FileWriter;

public class RecipesGenerator {
    public static void generate() {
        YoyoPluginHelper.YOYO_TIERS.forEach(t->{
            yoyo(t.getName().toLowerCase(),YoyoPluginHelper.CUSTOM_CORD.containsKey(t.getName().toLowerCase()) ? YoyoPluginHelper.CUSTOM_CORD.get(t.getName().toLowerCase()) + "_" : "",YoyoPluginHelper.CUSTOM_ITEM.getOrDefault(t.getName().toLowerCase(),""),YoyoPluginHelper.YOYO_BY_MODS.get(t.getName().toLowerCase()));
        });
    }



    private static void yoyo(String name,String cord,String item,String mod){
        try {
            FileWriter writer = new FileWriter(new File(PathConstant.RECIPES_PATH.toFile(), name+".json"));
            writer.write("{\n" +
                    "  \"type\": \"minecraft:crafting_shaped\",\n" +
                    "  \"category\": \"equipment\",\n" +
                    "  \"key\": {\n" +
                    "    \"C\": {\n" +
                    "      \"item\": \"yoyos:"+cord+"cord\"\n" +
                    "    },\n" +
                    "    \"I\": {\n" +
                    "      \"item\": \""+mod+":"+(item.isEmpty() ? name +"_ingot" : item)+"\"\n" +
                    "    },\n" +
                    "    \"S\": {\n" +
                    "      \"item\": \"minecraft:stick\"\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"pattern\": [\n" +
                    "    \" I \",\n" +
                    "    \"ISI\",\n" +
                    "    \"CI \"\n" +
                    "  ],\n" +
                    "  \"result\": {\n" +
                    "    \"item\": \"yoyos:"+name+"_yoyo\"\n" +
                    "  },\n" +
                    "  \"show_notification\": true\n" +
                    "}");
            writer.close();
        } catch (Exception exception){
            Yoyos.LOGGER.error("An error was detected when recipes generating",exception);
        }
    }

}
