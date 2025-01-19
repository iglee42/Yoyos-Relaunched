package fr.iglee42.yoyos.common;

import fr.iglee42.yoyos.common.init.YoyosEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Interaction {

    public static boolean collectItem(ItemStack yoyoStack, Player player, InteractionHand hand, YoyoEntity yoyo, Entity target){
        if (target instanceof ItemEntity && yoyo.isCollecting()){
            yoyo.collectDrop((ItemEntity) target);
            return true;
        }
        return false;
    }

    public static boolean attackEntity(ItemStack yoyoStack, Player player, InteractionHand hand, YoyoEntity yoyo, Entity target){
        if (!yoyo.canAttack() || !target.isAlive()) return false;
        if (!YoyoItem.isAttackEnable(yoyoStack)) return false;
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

    public static boolean craftWithBlock(ItemStack yoyoStack,Player player,BlockPos pos, BlockState state, Block block, YoyoEntity yoyo){
        if (!yoyo.canAttack()) return false;
        if (yoyo.isRetracting()) return false;
        if (player.level().isClientSide) return false;
        if (!YoyoItem.isEnchantmentEnable(yoyoStack,YoyosEnchantments.CRAFTING.getId())) return false;

        if (block.equals(Blocks.BEDROCK)){
            yoyo.createItemDropOrCollect(new ItemStack(Items.DIAMOND));
            yoyo.forceRetract();
        }
        return true;
    }


    public static boolean breakBlocks(ItemStack yoyoStack, Player player, BlockPos pos, BlockState state, Block block, YoyoEntity yoyo){
        if (!yoyo.canAttack() && !player.isCreative()) return false;
        if (yoyo.isRetracting()) return false;
        if (player.level().isClientSide) return false;
        if (yoyoStack.getEnchantmentLevel(YoyosEnchantments.BREAKING.get()) < 1) return false;
        if (!YoyoItem.isEnchantmentEnable(yoyoStack,YoyosEnchantments.BREAKING.getId())) return false;
        float blockSpeed = block.getDestroyProgress(state,player,player.level(),pos);

        if(blockSpeed <= ((YoyoItem)yoyoStack.getItem()).getTier().getSpeed()
                && blockSpeed >= 0 && block.canHarvestBlock(state,player.level(),pos,player)) {
            if (destroyBlock((ServerLevel) player.level(),pos, (ServerPlayer) player,((ServerPlayer)player).gameMode,yoyo))  {
                player.level().levelEvent(null, LevelEvent.PARTICLES_DESTROY_BLOCK,pos,Block.getId(state));
                yoyo.decrementRemainingTime(10);
                if (!player.isCreative()) yoyo.resetAttackCooldown();
            }
        }
        return true;
    }

    public static boolean destroyBlock(ServerLevel level, BlockPos pos, ServerPlayer player, ServerPlayerGameMode gameMode,YoyoEntity yoyo) {
        BlockState blockstate = level.getBlockState(pos);
        int exp = ForgeHooks.onBlockBreakEvent(level, gameMode.getGameModeForPlayer(), player, pos);
        if (exp == -1) {
            return false;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            Block block = blockstate.getBlock();
            if (block instanceof GameMasterBlock && !player.canUseGameMasterBlocks()) {
                level.sendBlockUpdated(pos, blockstate, blockstate, 3);
                return false;
            } else if (player.getMainHandItem().onBlockStartBreak(pos, player)) {
                return false;
            } else if (player.blockActionRestricted(level, pos, gameMode.getGameModeForPlayer())) {
                return false;
            } else if (gameMode.isCreative()) {
                removeBlock(level,pos, false,player);
                return true;
            } else {
                ItemStack itemstack = player.getMainHandItem();
                ItemStack itemstack1 = itemstack.copy();
                boolean flag1 = blockstate.canHarvestBlock(level, pos, player);
                itemstack.mineBlock(level, blockstate, pos, player);
                if (itemstack.isEmpty() && !itemstack1.isEmpty()) {
                    ForgeEventFactory.onPlayerDestroyItem(player, itemstack1, InteractionHand.MAIN_HAND);
                }

                boolean flag = removeBlock(level,pos, flag1,player);
                if (flag && flag1) {
                    if (!yoyo.isCollecting())block.playerDestroy(level, player, pos, blockstate, blockentity, itemstack1);
                    else {
                        player.awardStat(Stats.BLOCK_MINED.get(block));
                        player.causeFoodExhaustion(0.005F);
                        Block.getDrops(blockstate, level, pos, blockentity, player, itemstack1).forEach(yoyo::createItemDropOrCollect);
                        blockstate.spawnAfterBreak(level, pos, itemstack1, false);
                    }
                }

                if (flag && exp > 0) {
                    blockstate.getBlock().popExperience(level, pos, exp);
                }

                return true;
            }
        }
    }

    private static boolean removeBlock(Level level,BlockPos pos, boolean canHarvest,ServerPlayer player) {
        BlockState state = level.getBlockState(pos);
        boolean removed = state.onDestroyedByPlayer(level, pos, player, canHarvest, level.getFluidState(pos));
        if (removed)
            state.getBlock().destroy(level, pos, state);
        return removed;
    }




}
