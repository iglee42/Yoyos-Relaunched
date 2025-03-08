package fr.iglee42.yoyos.compat.tconstruct;

import fr.iglee42.yoyos.common.YoyoEntity;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
