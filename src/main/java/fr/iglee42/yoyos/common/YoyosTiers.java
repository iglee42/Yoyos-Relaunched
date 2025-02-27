package fr.iglee42.yoyos.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.iglee42.yoyos.Config;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class YoyosTiers {

    public static YoyoTier WOODEN;
    public static YoyoTier STONE;
    public static YoyoTier COPPER;
    public static YoyoTier IRON;
    public static YoyoTier GOLD;
    public static YoyoTier DIAMOND;
    public static YoyoTier NETHERITE;
    public static YoyoTier CREATIVE;

    public static List<YoyoTier> TIERS = new ArrayList<>();

    public static void init() throws IOException, IllegalAccessException {

        WOODEN = createTier("wooden",2.2,6.0,100,3.0, Tiers.WOOD);
        STONE = createTier("stone",4.0,7.0,200,4.0, Tiers.STONE);
        COPPER = createTier("copper",4.5,7.5,250,4.5, Tiers.GOLD);
        IRON = createTier("iron",5.0,8.0,300,5.0, Tiers.IRON);
        GOLD = createTier("gold",6.5,11.0,450,4.5, Tiers.GOLD);
        DIAMOND = createTier("diamond",4.0,9.0,500,6.0, Tiers.DIAMOND);
        NETHERITE = createTier("netherite",6.2,10.0,600,7.0, Tiers.NETHERITE);
        CREATIVE = createTier("creative",0.9,24.0,-1,256.0,Tiers.NETHERITE);

        if (Config.getConfigFile().exists()){
            for (Field f : Arrays.stream(YoyosTiers.class.getDeclaredFields()).filter(f->f.getType().equals(YoyoTier.class)).toList()){
                JsonObject config = new Gson().fromJson(new FileReader(Config.getConfigFile()),JsonObject.class);
                if (config.has(f.getName().toLowerCase())) f.set(null, YoyoTier.fromJson(config.get(f.getName().toLowerCase()).getAsJsonObject(), (YoyoTier) f.get(null)));
                else {
                    config.add(f.getName().toLowerCase(),((YoyoTier)f.get(null)).toJson());
                    FileWriter writer = new FileWriter(Config.getConfigFile());
                    writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(config));
                    writer.close();
                }
            }
        } else {
            JsonObject config = new JsonObject();
            for (Field f : Arrays.stream(YoyosTiers.class.getDeclaredFields()).filter(f->f.getType().equals(YoyoTier.class)).toList()){
                config.add(f.getName().toLowerCase(),((YoyoTier)f.get(null)).toJson());
            }
            FileWriter writer = new FileWriter(Config.getConfigFile());
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(config));
            writer.close();
        }

        TIERS.addAll(Arrays.asList(WOODEN,STONE,COPPER,IRON,GOLD,DIAMOND,NETHERITE,CREATIVE));

    }

    private static YoyoTier createTier(String name, double weight, double length, int duration, double damage, Tier tier){
        return new YoyoTier(name,weight,length,duration,damage,tier);
    }
}
