package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class ForbiddenArcanusPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "forbidden_arcanus";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo(new YoyoTier("deorum",Tiers.DIAMOND,modId())
                .setWeight(6.0)
                .setLength(11.0)
                .setDuration(550)
                .setDamage(6.5));
        helper.registerYoyo(new YoyoTier("obsidian",Tiers.DIAMOND,modId())
                .setWeight(6.5)
                .setLength(6.0)
                .setDuration(600)
                .setDamage(6.0));
    }
}
