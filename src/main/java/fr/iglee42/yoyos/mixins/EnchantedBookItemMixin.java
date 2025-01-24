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
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin {


    @Inject(method = "appendHoverText",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;appendEnchantmentNames(Ljava/util/List;Lnet/minecraft/nbt/ListTag;)V"),locals = LocalCapture.CAPTURE_FAILSOFT)
    private void yoyos$addYoyosEnchantments(ItemStack stack, Level p_41158_, List<Component> list, TooltipFlag p_41160_, CallbackInfo ci){
        ListTag enchs = EnchantedBookItem.getEnchantments(stack);
        for(int i = 0; i < enchs.size(); ++i) {
            CompoundTag compoundtag = enchs.getCompound(i);
            BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(compoundtag)).ifPresent((p_41708_) -> {
                if (ForgeRegistries.ENCHANTMENTS.getKey(p_41708_).getNamespace().equals(Yoyos.MODID)) list.add(p_41708_.getFullname(EnchantmentHelper.getEnchantmentLevel(compoundtag)));
                if (ForgeRegistries.ENCHANTMENTS.getKey(p_41708_).equals(YoyosEnchantments.CRAFTING.getId())) list.add(Component.literal("This enchantment is in WIP (work in progress), no usages of it are available").withStyle(ChatFormatting.RED));
            });
        }
    }
}
