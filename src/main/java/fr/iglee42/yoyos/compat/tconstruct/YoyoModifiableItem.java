package fr.iglee42.yoyos.compat.tconstruct;

import fr.iglee42.yoyos.common.YoyoEntity;
import fr.iglee42.yoyos.common.api.EntityInteraction;
import fr.iglee42.yoyos.common.api.IYoyo;
import fr.iglee42.yoyos.common.init.YoyosSounds;
import fr.iglee42.yoyos.compat.plugins.TconstructPlugin;
import fr.iglee42.yoyos.compat.tconstruct.parts.YoyoPlateMaterialStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.library.client.materials.MaterialTooltipCache;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.modules.build.SetStatModule;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.module.material.ToolPartsHook;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.IToolPart;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;
import java.util.UUID;

public class YoyoModifiableItem extends ModifiableItem implements IYoyo {
    public YoyoModifiableItem(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    @Override
    public double getWeight(ItemStack yoyo) {
        return ToolStack.from(yoyo).getStats().get(TconstructPlugin.WEIGHT_STAT);
    }

    @Override
    public double getLength(ItemStack yoyo) {
        return ToolStack.from(yoyo).getStats().get(TconstructPlugin.LENGTH_STAT);
    }

    @Override
    public int getDuration(ItemStack yoyo) {
        return (int) (ToolStack.from(yoyo).getStats().get(TconstructPlugin.DURATION_STAT) * 20);
    }

    @Override
    public int getCordColor(ItemStack yoyo, float ticks) {
        ToolStack item = ToolStack.from(yoyo);
        return MaterialTooltipCache.getColor(item.getMaterial(3).getVariant()).getValue();
    }

    @Override
    public int getAttackInterval(ItemStack yoyo) {
        return 10;
    }

    @Override
    public int getMaxCollectedDrops(ItemStack yoyo) {
        return 0;
    }

    @Override
    public <T extends LivingEntity> void damageItem(ItemStack yoyo, InteractionHand hand, int amount, T entity) {
        damageItem(yoyo,amount,entity,(e)->{});
    }

    @Override
    public void entityInteraction(ItemStack yoyoStack, Player player, InteractionHand hand, YoyoEntity yoyo, Entity target) {
        if (!target.level().isClientSide) return;
        new EntityInteraction() {
            @Override
            public boolean apply(ItemStack yoyoStack, Player player, InteractionHand hand, YoyoEntity yoyo, Entity target) {
                IToolStackView view = ToolStack.from(yoyoStack);
                if (view.isBroken()) return false;
                if (!yoyo.canAttack() || !target.isAttackable()) return false;
                ToolAttackUtil.attackEntity(view,player,hand,target,()->0,false);
                return true;
            }
        }.apply(yoyoStack,player,hand,yoyo,target);
    }

    @Override
    public boolean interactsWithBlocks(ItemStack yoyo) {
        return false;
    }

    @Override
    public void blockInteraction(ItemStack yoyoStack, Player player, Level world, BlockPos pos, BlockState state, Block block, YoyoEntity yoyo) {

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide){
            if (stack.getDamageValue() <= stack.getMaxDamage()){

                YoyoEntity yoyoEntity = YoyoEntity.CASTERS.get(player.getUUID());

                if (yoyoEntity == null) {
                    yoyoEntity = new YoyoEntity(level,player, hand);
                    level.addFreshEntity(yoyoEntity);
                    level.playSound(null, yoyoEntity.getX(), yoyoEntity.getY(), yoyoEntity.getZ(), YoyosSounds.THROW.get(), SoundSource.NEUTRAL, 0.5f, 0.4f / (level.random.nextFloat() * 0.4f + 0.8f));


                    player.causeFoodExhaustion(0.05f);
                } else {
                    yoyoEntity.setRetracting(!yoyoEntity.isRetracting());
                }
                return InteractionResultHolder.success(stack);

            }
        }

        return super.use(level,player,hand);
    }
}
