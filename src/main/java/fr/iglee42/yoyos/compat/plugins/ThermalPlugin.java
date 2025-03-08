package fr.iglee42.yoyos.compat.plugins;

import cofh.lib.util.Utils;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tiers;

import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.CommonHooks;

import java.util.UUID;

@YoyoPlugin
public class ThermalPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "thermal";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo(new YoyoTier("nickel",Tiers.IRON,modId())
                .setWeight(5.0)
                .setLength(8.0)
                .setDuration(300)
                .setDamage(5.0));
        helper.registerYoyo(new YoyoTier("tin",Tiers.IRON,modId())
                .setWeight(5.0)
                .setLength(8.0)
                .setDuration(300)
                .setDamage(5.0));
        helper.registerYoyo(new YoyoTier("lead",Tiers.IRON,modId())
                .setWeight(6.0)
                .setLength(8.0)
                .setDuration(300)
                .setDamage(5.0));
        helper.registerYoyo(new YoyoTier("silver",Tiers.IRON,modId())
                .setWeight(5.5)
                .setLength(8.0)
                .setDuration(300)
                .setDamage(5.0));
        helper.registerYoyo(new YoyoTier("bronze",Tiers.DIAMOND,modId())
                .setWeight(5.0)
                .setLength(9.0)
                .setDuration(500)
                .setDamage(6.0));
        helper.registerYoyo(new YoyoTier("constantan",Tiers.DIAMOND,modId())
                .setWeight(5.0)
                .setLength(9.0)
                .setDuration(500)
                .setDamage(6.0));
        helper.registerYoyo(new YoyoTier("invar",Tiers.DIAMOND,modId())
                .setWeight(5.0)
                .setLength(9.0)
                .setDuration(500)
                .setDamage(6.0));
        helper.registerYoyo(new YoyoTier("electrum",Tiers.DIAMOND,modId())
                .setWeight(6.0)
                .setLength(9.0)
                .setDuration(500)
                .setDamage(6.0));
        helper.registerYoyo(new YoyoTier("lumium",Tiers.NETHERITE,modId())
                .setWeight(3.5)
                .setLength(10.0)
                .setDuration(550)
                .setDamage(6.5)
                .addEntityInteraction((yoyoStack, player, hand, yoyo, target) -> {
                    if (!yoyo.canAttack() || !target.isAlive()) return false;
                    if (!YoyoItem.isAttackEnable(yoyoStack)) return false;
                    if (!CommonHooks.onPlayerAttackTarget(player, target)) return false;

                    UUID entityUUID = target.getUUID();

                    if ((player.getShoulderEntityLeft().contains("UUID") && entityUUID.equals(player.getShoulderEntityLeft().getUUID("UUID"))) || (player.getShoulderEntityRight().contains("UUID") && entityUUID.equals(player.getShoulderEntityRight().getUUID("UUID"))))
                        return false;

                    if (target.isAttackable()){
                        if (target instanceof LivingEntity lv)lv.addEffect(new MobEffectInstance(MobEffects.GLOWING,300));
                    }
                    return true;
                }));
        helper.registerYoyo(new YoyoTier("signalum",Tiers.NETHERITE,modId())
                .setWeight(4.0)
                .setLength(10.0)
                .setDuration(550)
                .setDamage(6.5));
        helper.registerYoyo(new YoyoTier("enderium",Tiers.NETHERITE,modId())
                .setWeight(4.5)
                .setLength(12.0)
                .setDuration(600)
                .setDamage(7.0)
                .addEntityInteraction((yoyoStack, player, hand, yoyo, target) -> {
                    if (!yoyo.canAttack() || !target.isAlive()) return false;
                    if (!YoyoItem.isAttackEnable(yoyoStack)) return false;
                    if (!CommonHooks.onPlayerAttackTarget(player, target)) return false;

                    UUID entityUUID = target.getUUID();

                    if ((player.getShoulderEntityLeft().contains("UUID") && entityUUID.equals(player.getShoulderEntityLeft().getUUID("UUID"))) || (player.getShoulderEntityRight().contains("UUID") && entityUUID.equals(player.getShoulderEntityRight().getUUID("UUID"))))
                        return false;

                    if (target.isAttackable()){
                        BlockPos randPos = yoyo.blockPosition().offset(-128 + yoyo.level().random.nextInt(257), yoyo.level().random.nextInt(8), -128 + yoyo.level().random.nextInt(257));

                        if (!yoyo.level().getBlockState(randPos).isSolid()) {
                            if (target instanceof LivingEntity) {
                                if (Utils.teleportEntityTo(target, randPos)) {
                                   // ((LivingEntity) target).addEffect(new MobEffectInstance(ENDERFERENCE.get(), 40, 0, false, false));
                                }
                            } else if (target.level().getGameTime() % 40 == 0) {
                                target.setPos(randPos.getX(), randPos.getY(), randPos.getZ());
                                target.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                            }
                        }
                    }
                    return true;
                }));


        if (ModList.get().isLoaded("thermal_integration")){
            helper.registerYoyo(new YoyoTier("steel",Tiers.IRON,modId())
                    .setWeight(5.5)
                    .setLength(9.0)
                    .setDuration(500)
                    .setDamage(6.0));
            helper.registerYoyo(new YoyoTier("rose_gold",Tiers.IRON,modId())
                    .setWeight(5.5)
                    .setLength(10.0)
                    .setDuration(500)
                    .setDamage(5.0));
        }
    }
}
