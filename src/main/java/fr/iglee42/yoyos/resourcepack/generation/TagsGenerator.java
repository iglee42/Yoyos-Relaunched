package fr.iglee42.yoyos.resourcepack.generation;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.resourcepack.PathConstant;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class TagsGenerator {

    public static void generate() {

        Yoyos.getPluginHelper().plugins.forEach(p->{
            Map<ResourceLocation,List<String>> tags = p.addTags();
            tags.forEach((tag,item)->{
                try {
                    writeTag(item,new File(PathConstant.ROOT_PATH.toFile(), "/data/"+tag.getNamespace()+"/tags/items/"+tag.getPath()+".json"));
                } catch (Exception e) {
                    Yoyos.LOGGER.error("An error was detected when tags generating",e);
                }
            });

        });
    }

    private static void writeTag(List<String> objects, File file) throws IOException {
        new File( file.getParent()).mkdirs();
        JsonObject tag = new JsonObject();
        tag.addProperty("replace",false);
        JsonArray values = new JsonArray();
        objects.forEach(values::add);
        tag.add("values",values);
        FileWriter writer = new FileWriter(file);
        writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(tag));
        writer.close();
    }

}
