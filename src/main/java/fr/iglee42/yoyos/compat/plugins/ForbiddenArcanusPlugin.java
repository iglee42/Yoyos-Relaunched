package fr.iglee42.yoyos.compat.plugins;

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
        helper.registerYoyo("deorum",6.0,11.0,550,6.5,Tiers.DIAMOND,this);
        helper.registerYoyo("obsidian",6.5,6.0,600,6.0, Tiers.DIAMOND,this);
    }
}
