package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.common.CommonHooks;

import java.util.UUID;

@YoyoPlugin
public class MekanismPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "mekanism";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo(new YoyoTier("tin",Tiers.IRON,modId())
                .setWeight(5.0)
                .setLength(8.0)
                .setDuration(300)
                .setDamage(5.0)
                .setCustomItem("ingot_tin"));
        helper.registerYoyo(new YoyoTier("lead",Tiers.IRON,modId())
                .setWeight(6.0)
                .setLength(8.0)
                .setDuration(300)
                .setDamage(5.0)
                .setCustomItem("ingot_lead"));
        helper.registerYoyo(new YoyoTier("osmium",Tiers.IRON,modId())
                .setWeight(4.5)
                .setLength(8.0)
                .setDuration(300)
                .setDamage(5.0)
                .setCustomItem("ingot_osmium"));
        helper.registerYoyo(new YoyoTier("uranium",Tiers.IRON,modId())
                .setWeight(6.5)
                .setLength(7.0)
                .setDuration(400)
                .setDamage(5.5)
                .setCustomItem("ingot_uranium")
                .addEntityInteraction((yoyoStack, player, hand, yoyo, target) -> {
                    if (!yoyo.canAttack() || !target.isAlive()) return false;
                    if (!YoyoItem.isAttackEnable(yoyoStack)) return false;
                    if (!CommonHooks.onPlayerAttackTarget(player, target)) return false;

                    UUID entityUUID = target.getUUID();

                    if ((player.getShoulderEntityLeft().contains("UUID") && entityUUID.equals(player.getShoulderEntityLeft().getUUID("UUID"))) || (player.getShoulderEntityRight().contains("UUID") && entityUUID.equals(player.getShoulderEntityRight().getUUID("UUID"))))
                        return false;

                    if (target.isAttackable()){
                        if (target instanceof LivingEntity lv)lv.addEffect(new MobEffectInstance(MobEffects.POISON,300));
                    }
                    return true;
                }));
        helper.registerYoyo(new YoyoTier("steel",Tiers.DIAMOND,modId())
                .setWeight(5.5)
                .setLength(9.0)
                .setDuration(500)
                .setDamage(6.0)
                .setCustomItem("ingot_steel"));
        helper.registerYoyo(new YoyoTier("bronze",Tiers.DIAMOND,modId())
                .setWeight(5.0)
                .setLength(9.0)
                .setDuration(500)
                .setDamage(6.0)
                .setCustomItem("ingot_bronze"));
        helper.registerYoyo(new YoyoTier("refined_glowstone",Tiers.DIAMOND,modId())
                .setWeight(4.0)
                .setLength(9.5)
                .setDuration(600)
                .setDamage(6.5)
                .setCustomItem("ingot_refined_glowstone"));
        helper.registerYoyo(new YoyoTier("refined_obsidian",Tiers.DIAMOND,modId())
                .setWeight(6.0)
                .setLength(9.5)
                .setDuration(600)
                .setDamage(6.5)
                .setCustomItem("ingot_refined_obsidian"));

    }
}
