package fr.iglee42.yoyos.compat.pneumaticcraft;

import fr.iglee42.yoyos.common.YoyoEntity;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.common.init.YoyosSounds;
import me.desht.pneumaticcraft.api.PNCCapabilities;
import me.desht.pneumaticcraft.api.pressure.IPressurizableItem;
import me.desht.pneumaticcraft.api.tileentity.IAirHandler;
import me.desht.pneumaticcraft.common.capabilities.AirHandlerItemStack;
import me.desht.pneumaticcraft.common.config.ConfigHelper;
import me.desht.pneumaticcraft.common.item.PressurizableItem;
import me.desht.pneumaticcraft.common.upgrades.ModUpgrades;
import me.desht.pneumaticcraft.common.util.PneumaticCraftUtils;
import me.desht.pneumaticcraft.common.util.UpgradableItemUtils;
import me.desht.pneumaticcraft.lib.PneumaticValues;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

import static me.desht.pneumaticcraft.common.item.PressurizableItem.roundedPressure;

public class PressurizedYoyoItem extends YoyoItem implements IPressurizableItem {

    private final int volume;
    private final float maxPressure;

    public PressurizedYoyoItem(YoyoTier tier) {
        super(tier);

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
        return stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY)
                .map(airHandler -> airHandler.getPressure() < airHandler.maxPressure())
                .orElse(false);
    }


    @Override
    public int getBarWidth(ItemStack pStack) {
        return pStack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY)
                .map(h -> Math.round(h.getPressure() / h.maxPressure() * 13F))
                .orElse(0);
    }


    @Override
    public int getBarColor(ItemStack pStack) {
        return getPressureDurabilityColor(pStack);
    }

    static int getPressureDurabilityColor(ItemStack stack) {
        return stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).map(airHandler -> {
            float f = airHandler.getPressure() / airHandler.maxPressure();
            int c = (int) (64 + 191 * f);
            return 0x40 << 16 | c << 8 | 0xFF;
        }).orElse(0xC0C0C0);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new AirHandlerItemStack(stack);
    }

    @Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        return ConfigHelper.common().advanced.nbtToClientModification.get() ? roundedPressure(stack) : super.getShareTag(stack);
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
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getInt(AirHandlerItemStack.AIR_NBT_KEY) : 0;
    }

    @Override
    public float getMaxPressure() {
        return maxPressure;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        IAirHandler airHandler = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElseThrow(RuntimeException::new);

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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> tooltips, TooltipFlag p_41424_) {
        stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).ifPresent((airHandler) -> {
            float f = airHandler.getPressure() / airHandler.maxPressure();
            ChatFormatting color;
            if (f < 0.1F) {
                color = ChatFormatting.RED;
            } else if (f < 0.5F) {
                color = ChatFormatting.GOLD;
            } else {
                color = ChatFormatting.DARK_GREEN;
            }

            tooltips.add(PneumaticCraftUtils.xlate("pneumaticcraft.gui.tooltip.pressure", PneumaticCraftUtils.roundNumberTo(airHandler.getPressure(), 1)).withStyle(color));
        });

        super.appendHoverText(stack, p_41422_, tooltips, p_41424_);
    }
}
