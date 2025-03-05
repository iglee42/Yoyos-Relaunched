package fr.iglee42.yoyos.common;

import fr.iglee42.yoyos.common.init.YoyosEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

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

        if ((player.getShoulderEntityLeft().contains("UUID") && entityUUID.equals(player.getShoulderEntityLeft().getUUID("UUID"))) || (player.getShoulderEntityRight().contains("UUID") && entityUUID.equals(player.getShoulderEntityRight().getUUID("UUID"))))
            return false;

        if (target.isAttackable()){
            if (!target.skipAttackInteraction(player)){
                yoyo.resetAttackCooldown();
                yoyo.decrementRemainingTime(10);
                yoyo.getYoyo().damageItem(yoyoStack,hand,1,player);

                float damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);

                float attackModifier = target instanceof LivingEntity lv ? EnchantmentHelper.getDamageBonus(yoyoStack,lv.getMobType()) : EnchantmentHelper.getDamageBonus(yoyoStack, MobType.UNDEFINED);

                float strength = player.getAttackStrengthScale(0.5f);

                damage *= 0.2f + strength * strength * 0.8f;
                attackModifier *= strength;
                if (damage > 0 || attackModifier > 0){
                    float knockbackModifier = (float) player.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
                    knockbackModifier += EnchantmentHelper.getKnockbackBonus(player);

                    boolean critical = attackModifier > 0.9f && player.fallDistance > 0.0F && !player.onGround() && !player.onClimbable() && !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS) && !player.isPassenger() && target instanceof LivingEntity && !player.isSprinting();

                    net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(player, target, critical, critical ? 1.5F : 1.0F);
                    critical = hitResult != null;

                    if (critical) damage *= hitResult.getDamageModifier();

                    damage += attackModifier;

                    float targetHealth = 0.0f;
                    boolean setEntityOnFire = false;
                    int fireAspect = EnchantmentHelper.getFireAspect(player);
                    if (ModList.get().isLoaded("twilightforest") && ForgeRegistries.ITEMS.getKey(yoyoStack.getItem()).getPath().equals("fiery_yoyo")){
                        fireAspect++;
                    }

                    if (target instanceof LivingEntity lv){
                        targetHealth = lv.getHealth();
                        if (fireAspect > 0 && !lv.isOnFire()){
                            setEntityOnFire = true;
                            lv.setSecondsOnFire(1);
                        }
                    }
                    Vec3 motion = target.getDeltaMovement();
                    boolean didDamage = target.hurt(player.level().damageSources().thrown(yoyo,player),damage);
                    if (didDamage){
                        if (knockbackModifier > 0){
                            if (target instanceof LivingEntity lv){
                                lv.knockback(knockbackModifier * 0.5F, Mth.sin(player.getYRot() * ((float)Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float)Math.PI / 180F)));
                            } else {
                                player.push(-Mth.sin(player.getYRot() * ((float)Math.PI / 180F)) * knockbackModifier * 0.5F, 0.1D, Mth.cos(player.getYRot() * ((float)Math.PI / 180F)) * knockbackModifier * 0.5F);
                            }

                            player.setDeltaMovement(player.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                            player.setSprinting(false);
                        }
                        if (target instanceof ServerPlayer && target.hurtMarked) {
                            ((ServerPlayer)target).connection.send(new ClientboundSetEntityMotionPacket(target));
                            target.hurtMarked = false;
                            target.setDeltaMovement(motion);
                        }

                        if (critical){
                            player.level().playSound(null, yoyo.getX(), yoyo.getY(), yoyo.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, player.getSoundSource(), 1.0F, 1.0F);
                            player.crit(target);
                        }

                        if (!critical){
                            if (attackModifier > 0.9f)
                                player.level().playSound(null, yoyo.getX(), yoyo.getY(), yoyo.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, player.getSoundSource(), 1.0F, 1.0F);
                            else
                                player.level().playSound(null, yoyo.getX(), yoyo.getY(), yoyo.getZ(), SoundEvents.PLAYER_ATTACK_WEAK, player.getSoundSource(), 1.0F, 1.0F);

                        }

                        if (attackModifier > 0.0f)
                            player.magicCrit(target);

                        player.setLastHurtMob(target);
                        if (target instanceof LivingEntity) {
                            EnchantmentHelper.doPostHurtEffects((LivingEntity)target, player);
                        }

                        EnchantmentHelper.doPostDamageEffects(player,target);

                        Entity entity = target;
                        if (target instanceof PartEntity<?> pe){
                            entity = pe.getParent();
                        }


                        if (!player.level().isClientSide && yoyoStack != ItemStack.EMPTY && entity instanceof LivingEntity){
                            ItemStack yoyoCopy = yoyoStack.copy();
                            yoyoStack.hurtEnemy((LivingEntity) entity,player);
                            if (yoyoStack.isEmpty()){
                                net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, yoyoCopy, InteractionHand.MAIN_HAND);
                                player.setItemInHand(hand,ItemStack.EMPTY);
                            }
                        }

                        if (target instanceof LivingEntity){
                            float damageDealt = targetHealth - ((LivingEntity) target).getHealth();
                            player.awardStat(Stats.DAMAGE_DEALT,Math.round(damageDealt * 10.0F));
                            if (fireAspect > 0)target.setSecondsOnFire(fireAspect * 4);
                            if (player.level() instanceof ServerLevel && damageDealt > 2.0f){
                                int k = (int) (damageDealt * 0.5D);
                                ((ServerLevel)player.level()).sendParticles(ParticleTypes.DAMAGE_INDICATOR,target.getX(),target.getY(0.5),target.getZ(),k,0.1D, 0.0D, 0.1D, 0.2D);
                            }
                            player.causeFoodExhaustion(0.3f);
                        }
                    } else {
                        player.level().playSound(null, yoyo.getX(), yoyo.getY(), yoyo.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, player.getSoundSource(), 1.0f, 1.0f);
                        if (setEntityOnFire)
                            target.extinguishFire();
                    }
                }
            } else {
                player.resetAttackStrengthTicker();
            }
        }
        return true;
    }

    public static boolean craftWithBlock(ItemStack yoyoStack,Player player,BlockPos pos, BlockState state, Block block, YoyoEntity yoyo){
        if (!yoyo.canAttack()) return false;
        if (yoyo.isRetracting()) return false;
        if (player.level().isClientSide) return false;
        if (!YoyoItem.isEnchantmentEnable(yoyoStack,YoyosEnchantments.CRAFTING.getId())) return false;

       /* if (block.equals(Blocks.BEDROCK)){
            yoyo.createItemDropOrCollect(new ItemStack(Items.DIAMOND));
            yoyo.forceRetract();
        }*/
        return false;
    }


    public static boolean breakBlocks(ItemStack yoyoStack, Player player, BlockPos pos, BlockState state, Block block, YoyoEntity yoyo){
        if (!yoyo.canAttack() && !player.isCreative()) return false;
        if (yoyo.isRetracting()) return false;
        if (player.level().isClientSide) return false;
        if (yoyoStack.getEnchantmentLevel(YoyosEnchantments.BREAKING.get()) < 1) return false;
        if (!YoyoItem.isEnchantmentEnable(yoyoStack,YoyosEnchantments.BREAKING.getId())) return false;
        float blockSpeed = block.getDestroyProgress(state,player,player.level(),pos);

        if(block.defaultDestroyTime() >= 0 && blockSpeed <= ((YoyoItem)yoyoStack.getItem()).getTier().getSpeed()
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
