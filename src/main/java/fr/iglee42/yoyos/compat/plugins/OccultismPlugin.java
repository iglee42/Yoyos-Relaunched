package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
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
        helper.registerYoyo(new YoyoTier("silver",Tiers.IRON,modId())
                .setWeight(5.5)
                .setLength(8.0)
                .setDuration(300)
                .setDamage(5.0));
        helper.registerYoyo(new YoyoTier("iesnium",Tiers.DIAMOND,modId())
                .setWeight(5.1)
                .setLength(9.5)
                .setDuration(550)
                .setDamage(6.5));
    }
}
