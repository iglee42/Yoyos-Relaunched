package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class RedstoneArsenalPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "redstone_arsenal";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo(new YoyoTier("flux",Tiers.DIAMOND,modId())
                .setWeight(6.5)
                .setLength(14.0)
                .setDuration(400)
                .setDamage(6.5));
    }
}
