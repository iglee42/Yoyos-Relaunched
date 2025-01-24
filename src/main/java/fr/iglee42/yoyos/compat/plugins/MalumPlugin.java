package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class MalumPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "malum";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo("hallowed_gold",6.5,12.0,550,5.0,Tiers.IRON,this);
        helper.registerYoyo("malignant_pewter",6.0,10.0,600,6.5,Tiers.NETHERITE,this);
        helper.registerYoyo("soul_stained_steel",5.5,9.0,450,5.5, Tiers.DIAMOND,this);
    }
}
