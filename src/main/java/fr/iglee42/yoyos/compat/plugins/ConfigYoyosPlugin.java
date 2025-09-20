package fr.iglee42.yoyos.compat.plugins;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.iglee42.igleelib.api.utils.JsonHelper;
import fr.iglee42.yoyos.Config;
import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.common.YoyosTiers;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.compat.configs.ConfigYoyoItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@YoyoPlugin
public class ConfigYoyosPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "config";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {}

    @Override
    public void registerYoyosAfterHelperInit(YoyoPluginHelper helper) {
        File yoyosDir = new File(FMLPaths.CONFIGDIR.get().toFile(), "yoyos/customs");
        yoyosDir.mkdirs();
        if (yoyosDir.listFiles((dir, name) -> name.endsWith(".json")) != null){
            for (File file : Objects.requireNonNull(yoyosDir.listFiles((dir, name) -> name.endsWith(".json")))) {
                JsonObject json;
                InputStreamReader reader = null;
                try {
                    reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                    json = JsonParser.parseReader(reader).getAsJsonObject();
                    if (json.get("id").getAsString().isEmpty()) throw new NullPointerException("The id can't be empty ! (" + file.getName() + ")" );
                    String[] splitId = JsonHelper.getString(json,"id").split(":");
                    String mod = splitId.length == 2 ? splitId[0] : "";
                    String name = splitId[splitId.length - 1];

                    double weight = GsonHelper.getAsDouble(json,"weight", YoyosTiers.DIAMOND.getWeight());
                    double length = GsonHelper.getAsDouble(json,"length", YoyosTiers.DIAMOND.getLength());
                    double damage = GsonHelper.getAsDouble(json,"damage", YoyosTiers.DIAMOND.getDamage());
                    int duration = JsonHelper.getIntOrDefault(json,"duration",YoyosTiers.DIAMOND.getDuration());

                    String item = JsonHelper.getStringOrDefault(json,"item","");
                    String cord = JsonHelper.getStringOrDefault(json,"cord","");
                    String stick = JsonHelper.getStringOrDefault(json,"stick","");

                    int cordColor = Integer.parseInt(JsonHelper.getStringOrDefault(json,"cordColor","FFFFFF"),16);

                    Tier tier = TierSortingRegistry.byName(ResourceLocation.parse(JsonHelper.getStringOrDefault(json,"tier","diamond")));

                    helper.registerYoyoAfterInit(new YoyoTier(name,tier,modId())
                            .setMod(mod)
                            .setWeight(weight)
                            .setLength(length)
                            .setDuration(duration)
                            .setDamage(damage)
                            .setCustomItem(item)
                            .setCustomCord(cord)
                            .setCustomStick(stick)
                            .setCustomConstructor(ConfigYoyoItem.class.getConstructor(YoyoTier.class,Integer.class))
                            .addCustomConstructorParameters(cordColor)
                    );

                    reader.close();
                } catch (Exception e) {
                    Yoyos.LOGGER.error("An error occurred while loading custom yoyos ({})", file.getName(), e);
                } finally {
                    IOUtils.closeQuietly(reader);
                }
            }
        }
    }

    @Override
    public boolean shouldLoad() {
        return true;
    }
}
