package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class OccultismPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "occultism";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo("silver",5.5,8.0,300,5.0,Tiers.IRON,this);
        helper.registerYoyo("iesnium",5.1,9.5,550,6.5, Tiers.DIAMOND,this);
    }
}
