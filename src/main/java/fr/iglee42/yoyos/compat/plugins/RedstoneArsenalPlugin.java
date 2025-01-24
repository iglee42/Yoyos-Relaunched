package fr.iglee42.yoyos.compat.plugins;

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
        helper.registerYoyo("flux",6.5,14.0,400,6.5, Tiers.DIAMOND,this);
    }
}
