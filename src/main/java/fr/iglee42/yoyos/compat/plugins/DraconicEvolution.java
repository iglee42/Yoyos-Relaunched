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
        helper.registerYoyo(new YoyoTier("wyvern",Tiers.DIAMOND,modId())
                .setWeight(6.0)
                .setLength(8.0)
                .setDuration(450)
                .setDamage(6.0)
                .setCustomRecipe("""
                        {
                          "type": "draconicevolution:fusion_crafting",
                          "catalyst": {
                            "item": "yoyos:diamond_yoyo"
                          },
                          "ingredients": [
                            {
                              "item": "draconicevolution:draconium_core"
                            },
                            {
                              "tag": "forge:ingots/draconium"
                            },
                            {
                              "tag": "forge:ingots/draconium"
                            },
                            {
                              "item": "draconicevolution:basic_relay_crystal"
                            },
                            {
                              "item": "draconicevolution:wyvern_energy_core"
                            },
                            {
                              "item": "draconicevolution:basic_relay_crystal"
                            }
                          ],
                          "result": {
                            "item": "yoyos:wyvern_yoyo"
                          },
                          "tier": "WYVERN",
                          "total_energy": 8000000
                        }"""));
        helper.registerYoyo(new YoyoTier("draconic",Tiers.NETHERITE,modId())
                .setWeight(7.0)
                .setLength(18.0)
                .setDuration(800)
                .setDamage(9.0)
                .setCustomRecipe("""
                        {
                          "type": "draconicevolution:fusion_crafting",
                          "catalyst": {
                            "item": "yoyos:wyvern_yoyo"
                          },
                          "ingredients": [
                            {
                              "tag": "forge:ingots/netherite"
                            },
                            {
                              "item": "draconicevolution:wyvern_core"
                            },
                            {
                              "tag": "forge:ingots/netherite"
                            },
                            {
                              "tag": "forge:ingots/draconium_awakened"
                            },
                            {
                              "tag": "forge:ingots/draconium_awakened"
                            },
                            {
                              "tag": "forge:ingots/netherite"
                            },
                            {
                              "item": "draconicevolution:draconic_energy_core"
                            },
                            {
                              "tag": "forge:ingots/netherite"
                            }
                          ],
                          "result": {
                            "item": "yoyos:draconic_yoyo"
                          },
                          "tier": "DRACONIC",
                          "total_energy": 32000000
                        }"""));
        helper.registerYoyo(new YoyoTier("chaotic",Tiers.NETHERITE,modId())
                .setWeight(7.5)
                .setLength(18.0)
                .setDuration(800)
                .setDamage(10.0)
                .setCustomRecipe("""
                        {
                          "type": "draconicevolution:fusion_crafting",
                          "catalyst": {
                            "item": "yoyos:draconic_yoyo"
                          },
                          "ingredients": [
                            {
                              "tag": "forge:ingots/draconium_awakened"
                            },
                            {
                              "item": "draconicevolution:chaotic_core"
                            },
                            {
                              "tag": "forge:ingots/draconium_awakened"
                            },
                            {
                              "tag": "forge:ingots/draconium_awakened"
                            },
                            {
                              "tag": "forge:ingots/draconium_awakened"
                            },
                            {
                              "tag": "forge:ingots/draconium_awakened"
                            },
                            {
                              "item": "draconicevolution:chaotic_energy_core"
                            },
                            {
                              "tag": "forge:ingots/draconium_awakened"
                            }
                          ],
                          "result": {
                            "item": "yoyos:chaotic_yoyo"
                          },
                          "tier": "CHAOTIC",
                          "total_energy": 128000000
                        }"""));
    }
}
