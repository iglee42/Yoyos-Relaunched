package fr.iglee42.yoyos.mixins;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.init.YoyosEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();

    @Shadow public abstract ListTag getEnchantmentTags();

    @Inject(method = "getTooltipLines",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;appendEnchantmentNames(Ljava/util/List;Lnet/minecraft/nbt/ListTag;)V"),locals = LocalCapture.CAPTURE_FAILSOFT)
    private void yoyos$addYoyosEnchantments(Player p_41652_, TooltipFlag p_41653_, CallbackInfoReturnable<List<Component>> cir, List<Component> list, MutableComponent mutablecomponent, int j){
        ListTag enchs = getEnchantmentTags();
        for(int i = 0; i < enchs.size(); ++i) {
            CompoundTag compoundtag = enchs.getCompound(i);
            BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(compoundtag)).ifPresent((p_41708_) -> {
                if (ForgeRegistries.ENCHANTMENTS.getKey(p_41708_).getNamespace().equals(Yoyos.MODID) && !(getItem() instanceof YoyoItem)) list.add(p_41708_.getFullname(EnchantmentHelper.getEnchantmentLevel(compoundtag)));
                if (ForgeRegistries.ENCHANTMENTS.getKey(p_41708_).equals(YoyosEnchantments.CRAFTING.getId()) && !(getItem() instanceof YoyoItem)) list.add(Component.literal("This enchantment is in WIP (work in progress), no usages of it are available").withStyle(ChatFormatting.RED));

            });
        }
    }

    @Inject(method = "lambda$appendEnchantmentNames$5",at = @At(value = "HEAD"),locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private static void yoyos$hideYoyosEnchantments(List p_41710_, CompoundTag compoundtag, Enchantment p_41708_, CallbackInfo ci){
        if (ForgeRegistries.ENCHANTMENTS.getKey(p_41708_).getNamespace().equals(Yoyos.MODID)) ci.cancel();
    }

}
