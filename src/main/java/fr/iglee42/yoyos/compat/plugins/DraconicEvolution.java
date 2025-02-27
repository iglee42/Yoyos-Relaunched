package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
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
        helper.registerYoyo(new YoyoTier("draconium",Tiers.DIAMOND,modId())
                .setWeight(6.0)
                .setLength(8.0)
                .setDuration(450)
                .setDamage(6.0));
        helper.registerYoyo(new YoyoTier("awakened_draconium",Tiers.NETHERITE,modId())
                .setWeight(7.0)
                .setLength(18.0)
                .setDuration(800)
                .setDamage(9.0));
        helper.registerYoyo(new YoyoTier("chaotic",Tiers.NETHERITE,modId())
                .setWeight(7.5)
                .setLength(18.0)
                .setDuration(800)
                .setDamage(10.0));
    }
}
