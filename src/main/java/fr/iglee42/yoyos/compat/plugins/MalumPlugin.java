package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
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
        helper.registerYoyo(new YoyoTier("hallowed_gold",Tiers.IRON,modId())
                .setWeight(6.5)
                .setLength(12.0)
                .setDuration(550)
                .setDamage(5.0));
        helper.registerYoyo(new YoyoTier("malignant_pewter",Tiers.NETHERITE,modId())
                .setWeight(6.0)
                .setLength(10.0)
                .setDuration(600)
                .setDamage(6.5));
        helper.registerYoyo(new YoyoTier("soul_stained_steel",Tiers.DIAMOND,modId())
                .setWeight(5.5)
                .setLength(9.0)
                .setDuration(450)
                .setDamage(5.5));
    }
}
