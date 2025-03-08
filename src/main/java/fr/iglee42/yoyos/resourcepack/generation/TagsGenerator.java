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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fr.iglee42.yoyos.Yoyos.MODID;


public class TagsGenerator {

    public static void generate() {

        List<String> yoyos = new ArrayList<>();
        Yoyos.getPluginHelper().YOYO_TIERS.forEach(t->{
            yoyos.add(MODID + ":" + t.getName().toLowerCase() + "_yoyo");
        });

        try {
            writeTag(yoyos,new File(PathConstant.ITEMS_TAGS_PATH.toFile(),"yoyos.json"));
            writeTag(yoyos,new File(PathConstant.MC_ITEM_TAGS_PATH.toFile(),"enchantable/sharp_weapon.json"));
            writeTag(yoyos,new File(PathConstant.MC_ITEM_TAGS_PATH.toFile(),"enchantable/mining_loot.json"));
            writeTag(yoyos,new File(PathConstant.MC_ITEM_TAGS_PATH.toFile(),"enchantable/durability.json"));
        } catch (IOException e) {
            Yoyos.LOGGER.error("An error was detected when tags generating",e);
        }

        Yoyos.getPluginHelper().plugins.forEach(p->{
            Map<ResourceLocation,List<String>> tags = p.addTags();
            tags.forEach((tag,item)->{
                try {
                    writeTag(item,new File(PathConstant.ROOT_PATH.toFile(), "/data/"+tag.getNamespace()+"/tags/item/"+tag.getPath()+".json"));
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
