package fr.iglee42.yoyos.compat.botania;

import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.CustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import java.util.function.Consumer;

public class TerrasteelYoyoItem extends ManaYoyoItem {

    private static final int MANA_PER_DAMAGE = 100;

    public TerrasteelYoyoItem(YoyoTier tier) {
        super(tier);
    }

    @Override
    public int getManaPerDamage() {
        return MANA_PER_DAMAGE;
    }
}
