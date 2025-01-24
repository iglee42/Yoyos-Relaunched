package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fml.ModList;

@YoyoPlugin
public class CreatePlugin implements IYoyoPlugin {
    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo("andesite_alloy",4.5,7.5,250,4.5,Tiers.STONE,this);
        helper.registerYoyo("zinc",5.0,8.0,300,5.0,Tiers.IRON,this);
        helper.registerYoyo("brass",5.0,9.0,400,5.5,Tiers.DIAMOND,this);

        helper.setCustomItem("andesite_alloy","andesite_alloy");
    }

    @Override
    public String modId() {
        return "create";
    }
}
