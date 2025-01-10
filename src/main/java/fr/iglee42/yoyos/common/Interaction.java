package fr.iglee42.yoyos.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Interaction {

    public static boolean attackEntity(ItemStack yoyoStack, Player player, InteractionHand hand, YoyoEntity yoyo, Entity target){
        if (!yoyo.canAttack() || !target.isAlive()) return false;
        if (!ForgeHooks.onPlayerAttackTarget(player, target)) return false;

        UUID entityUUID = target.getUUID();


        if (target.isAttackable()){
            if (!target.skipAttackInteraction(player)){
                yoyo.resetAttackCooldown();
                yoyo.decrementRemainingTime(10);
                yoyo.getYoyo().damageItem(yoyoStack,hand,1,player);

                var damage = player.getAttribute(Attributes.ATTACK_DAMAGE).getValue();

                target.hurt(player.level().damageSources().mobProjectile(yoyo,player), (float) damage);
            }
        }
        return true;
    }

    private static Map<BlockPos,Integer> BREAKING_PROGRESS = new HashMap<>();

    public static boolean breakBlocks(ItemStack yoyoStack, Player player, BlockPos pos, BlockState state, Block block, YoyoEntity yoyo){
        if (!yoyo.canAttack()) return false;
        player.level().destroyBlock(pos,!player.getAbilities().instabuild,player);
        yoyo.decrementRemainingTime(10);
        yoyo.resetAttackCooldown();
        return true;
    }

}
