package fr.iglee42.yoyos.compat.botania;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import fr.iglee42.yoyos.common.YoyoTier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.common.handler.PixieHandler;

public class ElementiumYoyoItem extends ManaYoyoItem {


    public ElementiumYoyoItem(YoyoTier tier) {
        super(tier);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot,stack);
        if (slot == EquipmentSlot.MAINHAND) {
            ret = HashMultimap.create(ret);
            ret.put(PixieHandler.PIXIE_SPAWN_CHANCE, PixieHandler.makeModifier(slot, "Sword modifier", 1));
        }
        return ret;
    }


    @Override
    public int getMaxCollectedDrops(ItemStack yoyo) {
        return 64 + super.getMaxCollectedDrops(yoyo);
    }
}
