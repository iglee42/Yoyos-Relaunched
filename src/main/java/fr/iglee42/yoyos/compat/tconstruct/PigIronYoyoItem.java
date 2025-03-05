package fr.iglee42.yoyos.compat.tconstruct;

import cofh.core.util.ProxyUtils;
import cofh.lib.api.item.ICoFHItem;
import cofh.redstonearsenal.common.item.IFluxItem;
import fr.iglee42.yoyos.common.YoyoEntity;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.common.init.YoyosSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class PigIronYoyoItem extends YoyoItem {


    public PigIronYoyoItem(YoyoTier tier) {
        super(tier);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            YoyoEntity yoyoEntity = YoyoEntity.CASTERS.get(player.getUUID());
            if (yoyoEntity == null) {
                yoyoEntity = factory.create(level, player, hand);
                level.addFreshEntity(yoyoEntity);
                level.playSound(null, yoyoEntity.getX(), yoyoEntity.getY(), yoyoEntity.getZ(), SoundEvents.PIG_HURT, SoundSource.NEUTRAL, 0.5f, 1f);
                player.causeFoodExhaustion(0.05f);
            } else {
                yoyoEntity.setRetracting(!yoyoEntity.isRetracting());
            }

        }
        return InteractionResultHolder.success(stack);
    }

    @Override
    public int getCordColor(ItemStack yoyo, float ticks) {
        return 0xA74E21;
    }
}
