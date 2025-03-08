package fr.iglee42.yoyos.mixins;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.init.YoyosEnchantments;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();

    @Inject(method = "addToTooltip",remap = false,at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/TooltipProvider;addToTooltip(Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V",shift = At.Shift.BEFORE),locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private <T extends TooltipProvider> void yoyos$addYoyosEnchantments(DataComponentType<T> type, Item.TooltipContext ctx, Consumer<Component> p_331885_, TooltipFlag p_331177_, CallbackInfo ci, TooltipProvider t){
        if (type.equals(DataComponents.ENCHANTMENTS) && getItem() instanceof YoyoItem){
            ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable((ItemEnchantments) t);
            enchantments.removeIf(h-> Objects.equals(h.getKey(), YoyosEnchantments.COLLECTING));
            enchantments.removeIf(h-> Objects.equals(h.getKey(), YoyosEnchantments.BREAKING));
            enchantments.removeIf(h-> Objects.equals(h.getKey(), YoyosEnchantments.CRAFTING));
            enchantments.toImmutable().addToTooltip(ctx,p_331885_,p_331177_);
            ci.cancel();
        }
    }


}
