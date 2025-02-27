package fr.iglee42.yoyos.compat.botania;

import fr.iglee42.yoyos.common.YoyoTier;
import net.minecraft.world.item.ItemStack;

public class GaiaYoyoItem extends ManaYoyoItem{
    public GaiaYoyoItem(YoyoTier tier) {
        super(tier);
    }

    @Override
    public int getMaxCollectedDrops(ItemStack yoyo) {
        return 128 + super.getMaxCollectedDrops(yoyo);
    }
}
