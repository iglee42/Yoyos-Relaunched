package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
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
        helper.registerYoyo(new YoyoTier("compressed_iron",Tiers.IRON,modId())
                .setWeight(5.5)
                .setLength(7.5)
                .setDuration(350)
                .setDamage(5.5)
                .setCustomItem("ingot_iron_compressed"));
    }
}
