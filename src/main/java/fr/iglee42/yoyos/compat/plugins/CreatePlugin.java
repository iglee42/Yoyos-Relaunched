package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class CreatePlugin implements IYoyoPlugin {
    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo(new YoyoTier("andesite_alloy",Tiers.STONE,modId())
                .setWeight(4.5)
                .setLength(7.5)
                .setDuration(250)
                .setDamage(4.5)
                .setCustomItem("andesite_alloy"));
        helper.registerYoyo(new YoyoTier("zinc",Tiers.IRON,modId())
                .setWeight(5.0)
                .setLength(8.0)
                .setDuration(300)
                .setDamage(5.0));
        helper.registerYoyo(new YoyoTier("brass",Tiers.DIAMOND,modId())
                .setWeight(5.0)
                .setLength(9.0)
                .setDuration(400)
                .setDamage(5.5));
    }

    @Override
    public String modId() {
        return "create";
    }
}
