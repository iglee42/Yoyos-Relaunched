package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.compat.tconstruct.PigIronYoyoItem;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class TconstructPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "tconstruct";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo(new YoyoTier("amethyst_bronze", Tiers.IRON,modId())
                .setWeight(5.0)
                .setLength(8.0)
                .setDuration(300)
                .setDamage(5.5));
        helper.registerYoyo(new YoyoTier("cobalt",Tiers.DIAMOND,modId())
                .setWeight(4.5)
                .setLength(10.0)
                .setDuration(400)
                .setDamage(6.0));
        helper.registerYoyo(new YoyoTier("hepatizon",Tiers.DIAMOND,modId())
                .setWeight(5.0)
                .setLength(10.0)
                .setDuration(500)
                .setDamage(7.0));
        helper.registerYoyo(new YoyoTier("manyullyn",Tiers.NETHERITE,modId())
                .setWeight(6.0)
                .setLength(11.0)
                .setDuration(600)
                .setDamage(7.5));
        try {
            helper.registerYoyo(new YoyoTier("pigiron",Tiers.IRON,modId())
                    .setWeight(4.5)
                    .setLength(8.0)
                    .setDuration(250)
                    .setDamage(5.5)
                    .setCustomConstructor(PigIronYoyoItem.class.getConstructor(YoyoTier.class)));
        } catch (NoSuchMethodException ignored) {}
        helper.registerYoyo(new YoyoTier("queens_slime",Tiers.DIAMOND,modId())
                .setWeight(6.0)
                .setLength(11.0)
                .setDuration(500)
                .setDamage(7.0));
        helper.registerYoyo(new YoyoTier("rose_gold",Tiers.IRON,modId())
                .setWeight(5.5)
                .setLength(10.0)
                .setDuration(500)
                .setDamage(5.0));
        helper.registerYoyo(new YoyoTier("slimesteel",Tiers.IRON,modId())
                .setWeight(5.5)
                .setLength(9.0)
                .setDuration(350)
                .setDamage(6.0));
    }
}
