package fr.iglee42.yoyos.compat.adastra;

import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import net.minecraft.world.item.ItemStack;

public class AdAstraYoyoItem extends YoyoItem {
    public AdAstraYoyoItem(YoyoTier tier) {
        super(tier);
    }

    @Override
    public int getCordColor(ItemStack yoyo, float ticks) {
        return 0xF0B025;
    }
}
