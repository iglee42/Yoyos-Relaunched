package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
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
        helper.registerYoyo(new YoyoTier("depth",Tiers.NETHERITE,modId())
                .setWeight(6.0)
                .setLength(11.0)
                .setDuration(550)
                .setDamage(6.5));
        helper.registerYoyo(new YoyoTier("sky",Tiers.DIAMOND,modId())
                .setWeight(4.0)
                .setLength(8.0)
                .setDuration(350)
                .setDamage(5.5));
    }
}
