package fr.iglee42.yoyos.compat.configs;

import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.common.api.YoyoFactory;
import net.minecraft.world.item.ItemStack;

public class ConfigYoyoItem extends YoyoItem {

    private final int cordColor;

    public ConfigYoyoItem(YoyoTier tier,Integer cordColor) {
        super(tier);
        this.cordColor = cordColor;
    }

    @Override
    public int getCordColor(ItemStack yoyo, float ticks) {
        return cordColor;
    }
}
