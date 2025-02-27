package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class TwilightForestPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "twilightforest";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo(new YoyoTier("ironwood", Tiers.IRON,modId())
                .setWeight(4.0)
                .setLength(9.0)
                .setDuration(350)
                .setDamage(5.5));
        helper.registerYoyo(new YoyoTier("naga_scale",Tiers.IRON,modId())
                .setWeight(4.5)
                .setLength(9.0)
                .setDuration(350)
                .setDamage(5.5));
        helper.registerYoyo(new YoyoTier("knightmetal",Tiers.DIAMOND,modId())
                .setWeight(5.0)
                .setLength(9.0)
                .setDuration(450)
                .setDamage(6.0));
        helper.registerYoyo(new YoyoTier("steeleaf",Tiers.DIAMOND,modId())
                .setWeight(4.0)
                .setLength(10.0)
                .setDuration(450)
                .setDamage(6.0));
        helper.registerYoyo(new YoyoTier("fiery",Tiers.NETHERITE,modId())
                .setWeight(6.0)
                .setLength(11.0)
                .setDuration(600)
                .setDamage(8.0));
    }
}
