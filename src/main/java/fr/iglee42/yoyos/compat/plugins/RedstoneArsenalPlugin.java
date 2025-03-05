package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.compat.redstonearsenal.FluxYoyoItem;
import net.minecraft.world.item.Tiers;

@YoyoPlugin
public class RedstoneArsenalPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "redstone_arsenal";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        try {
            helper.registerYoyo(new YoyoTier("flux",Tiers.DIAMOND,modId())
                    .setWeight(6.5)
                    .setLength(14.0)
                    .setDuration(400)
                    .setDamage(6.5)
                    .setCustomConstructor(FluxYoyoItem.class.getConstructor(YoyoTier.class))
                    .setCustomStick("redstone_arsenal:flux_obsidian_rod")
                    .setCustomModel("""
                            {
                              "parent": "minecraft:item/handheld",
                              "textures": {
                                "layer0": "yoyos:item/redstone_arsenal/flux"
                              },
                              "overrides": [
                                {
                                  "predicate": {
                                    "charged": 1.0
                                  },
                                  "model": "yoyos:item/flux_yoyo_charged"
                                }
                              ]
                            }"""));
        } catch (NoSuchMethodException ignored) {}
    }
}
