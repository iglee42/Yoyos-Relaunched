package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class PneumaticCraftPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "pneumaticcraft";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo("compressed_iron",5.5,7.5,350,5.5, Tiers.IRON,this);

        helper.setCustomItem("compressed_iron","ingot_iron_compressed");
    }
}
