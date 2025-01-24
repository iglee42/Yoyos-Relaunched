package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class MekanismPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "mekanism";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo("tin",5.0,8.0,300,5.0, Tiers.IRON,this);
        helper.registerYoyo("lead",6.0,8.0,300,5.0,Tiers.IRON,this);
        helper.registerYoyo("osmium",4.5,8.0,300,5.0,Tiers.IRON,this);
        helper.registerYoyo("uranium",6.5,7.0,400,5.5,Tiers.IRON,this);
        helper.registerYoyo("steel",5.5,9.0,500,6.0,Tiers.DIAMOND,this);
        helper.registerYoyo("bronze",5.0,9.0,500,6.0,Tiers.DIAMOND,this);
        helper.registerYoyo("refined_glowstone",4.0,9.5,600,6.5,Tiers.DIAMOND,this);
        helper.registerYoyo("refined_obsidian",6.0,9.5,600,6.5,Tiers.DIAMOND,this);

        YoyoPluginHelper.YOYO_BY_MODS.keySet().stream().filter(s->YoyoPluginHelper.YOYO_BY_MODS.get(s).equals("mekanism")).forEach(y->{
            helper.setCustomItem(y,"ingot_"+y);
        });
    }
}
