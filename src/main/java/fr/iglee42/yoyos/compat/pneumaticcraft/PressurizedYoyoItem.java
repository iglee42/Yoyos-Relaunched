package fr.iglee42.yoyos.compat.pneumaticcraft;

import fr.iglee42.yoyos.common.YoyoEntity;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.common.init.YoyosSounds;
import me.desht.pneumaticcraft.api.PNCCapabilities;
import me.desht.pneumaticcraft.api.pressure.IPressurizableItem;
import me.desht.pneumaticcraft.api.tileentity.IAirHandler;
import me.desht.pneumaticcraft.common.registry.ModDataComponents;
import me.desht.pneumaticcraft.common.upgrades.ModUpgrades;
import me.desht.pneumaticcraft.common.upgrades.UpgradableItemUtils;
import me.desht.pneumaticcraft.lib.PneumaticValues;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class PressurizedYoyoItem extends YoyoItem implements IPressurizableItem {

    private final int volume;
    private final float maxPressure;

    public PressurizedYoyoItem(YoyoTier tier) {
        super(new Properties().stacksTo(1).component(ModDataComponents.AIR,0),tier);

        this.volume = PneumaticValues.VORTEX_CANNON_VOLUME;
        this.maxPressure = (float) PneumaticValues.VORTEX_CANNON_MAX_AIR  / volume;
    }



    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return shouldShowPressureDurability(stack);
    }

    static boolean shouldShowPressureDurability(ItemStack stack) {
        return PNCCapabilities.getAirHandler(stack)
                .map(airHandler -> airHandler.getPressure() < airHandler.maxPressure())
                .orElse(false);
    }


    @Override
    public int getBarWidth(ItemStack stack) {
        return PNCCapabilities.getAirHandler(stack)
                .map(h -> Math.round(h.getPressure() / h.maxPressure() * 13F))
                .orElse(0);
    }



    @Override
    public int getBarColor(ItemStack pStack) {
        return getPressureDurabilityColor(pStack);
    }

    static int getPressureDurabilityColor(ItemStack stack) {
        return PNCCapabilities.getAirHandler(stack).map(airHandler -> {
            float f = airHandler.getPressure() / airHandler.maxPressure();
            int c = (int) (64 + 191 * f);
            return 0x40 << 16 | c << 8 | 0xFF;
        }).orElse(0xC0C0C0);
    }


    @Override
    public int getBaseVolume() {
        return volume;
    }

    @Override
    public int getVolumeUpgrades(ItemStack itemStack) {
        return UpgradableItemUtils.getUpgradeCount(itemStack, ModUpgrades.VOLUME.get());
    }

    @Override
    public int getAir(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.AIR.get(), 0);
    }

    @Override
    public float getMaxPressure() {
        return maxPressure;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<Item> onBroken) {
        IAirHandler airHandler = PNCCapabilities.getAirHandler(stack).orElseThrow(RuntimeException::new);

        airHandler.addAir(-Math.min(airHandler.getAir(),amount * 50));
        return 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide){
            if (getPressure(stack) > 0.1){

                YoyoEntity yoyoEntity = YoyoEntity.CASTERS.get(player.getUUID());

                if (yoyoEntity == null) {
                    yoyoEntity = factory.create(level,player, hand);
                    level.addFreshEntity(yoyoEntity);
                    level.playSound(null, yoyoEntity.getX(), yoyoEntity.getY(), yoyoEntity.getZ(), YoyosSounds.THROW.get(), SoundSource.NEUTRAL, 0.5f, 0.4f / (level.random.nextFloat() * 0.4f + 0.8f));


                    player.causeFoodExhaustion(0.05f);
                } else {
                    yoyoEntity.setRetracting(!yoyoEntity.isRetracting());
                }

            }
        }

        return InteractionResultHolder.success(stack);
    }

}
