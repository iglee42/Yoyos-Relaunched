package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class DraconicEvolution implements IYoyoPlugin {
    @Override
    public String modId() {
        return "draconicevolution";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo("draconium",6.0,8.0,450,6.0,Tiers.DIAMOND,this);
        helper.registerYoyo("awakened_draconium",7.0,18.0,800,9.0, Tiers.NETHERITE,this);
    }
}
