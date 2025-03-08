package fr.iglee42.yoyos.resourcepack.generation;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.resourcepack.PathConstant;

import java.io.File;
import java.io.FileWriter;

public class RecipesGenerator {
    public static void generate() {
        Yoyos.getPluginHelper().YOYO_TIERS.forEach(t->{
            if (t.getCustomRecipe().isEmpty())
                yoyo(t.getName().toLowerCase(),
                    t.hasCustomCord() ? t.getCustomCord() + "_" : "",
                    t.getCustomItem(),
                    t.hasCustomMod() ? t.getMod() : t.getPlugin(),
                    t.hasCustomStick() ? t.getCustomStick() : "minecraft:stick");
            else{
                try {
                    FileWriter writer = new FileWriter(new File(PathConstant.RECIPES_PATH.toFile(), t.getName().toLowerCase()+"_yoyo.json"));
                    writer.write(t.getCustomRecipe());
                    writer.close();
                } catch (Exception exception){
                    Yoyos.LOGGER.error("An error was detected when recipes generating",exception);
                }
            }
        });
    }



    private static void yoyo(String name,String cord,String item,String mod,String stick){
        try {
            FileWriter writer = new FileWriter(new File(PathConstant.RECIPES_PATH.toFile(), name+"_yoyo.json"));
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
                    "      \"item\": \""+stick+"\"\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"pattern\": [\n" +
                    "    \" I \",\n" +
                    "    \"ISI\",\n" +
                    "    \"CI \"\n" +
                    "  ],\n" +
                    "  \"result\": {\n" +
                    "    \"id\": \"yoyos:"+name+"_yoyo\"\n" +
                    "  },\n" +
                    "  \"show_notification\": true\n" +
                    "}");
            writer.close();
        } catch (Exception exception){
            Yoyos.LOGGER.error("An error was detected when recipes generating",exception);
        }
    }

}
