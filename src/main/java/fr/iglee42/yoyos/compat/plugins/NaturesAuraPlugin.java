package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class NaturesAuraPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "naturesaura";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo("depth",6.0,11.0,550,6.5,Tiers.NETHERITE,this);
        helper.registerYoyo("sky",4.0,8.0,350,5.5, Tiers.DIAMOND,this);
    }
}
