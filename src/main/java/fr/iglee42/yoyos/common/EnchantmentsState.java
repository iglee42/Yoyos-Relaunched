package fr.iglee42.yoyos.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.iglee42.yoyos.common.init.YoyosEnchantments;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public class EnchantmentsState {

    private static final Codec<Object2BooleanArrayMap<ResourceKey<Enchantment>>> ENCHS_CODEC = Codec.unboundedMap(ResourceKey.codec(Registries.ENCHANTMENT), Codec.BOOL).xmap(Object2BooleanArrayMap::new, Function.identity());
    public static final Codec<EnchantmentsState> CODEC = RecordCodecBuilder.create((p_337961_) -> p_337961_.group(ENCHS_CODEC.fieldOf("enchantments").forGetter((p_340785_) -> p_340785_.enchantments)).apply(p_337961_, EnchantmentsState::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, EnchantmentsState> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.map(Object2BooleanArrayMap::new, ResourceKey.streamCodec(Registries.ENCHANTMENT), ByteBufCodecs.BOOL), (p_340784_) -> p_340784_.enchantments, EnchantmentsState::new);
    public static final EnchantmentsState EMPTY = new EnchantmentsState(new Object2BooleanArrayMap<>()) ;

    private final Object2BooleanArrayMap<ResourceKey<Enchantment>> enchantments;


    public EnchantmentsState(Object2BooleanArrayMap<ResourceKey<Enchantment>> enchantments) {
        this.enchantments = enchantments;
    }


    public boolean isEnchantmentActivate(ItemStack stack, ResourceKey<Enchantment> enchantment, HolderLookup.Provider provider){
        if (!(stack.getItem() instanceof YoyoItem)) return false;
        if (enchantment.equals(YoyosEnchantments.COLLECTING)){
            return ((YoyoItem)stack.getItem()).getMaxCollectedDrops(stack,provider) > 0 && (!enchantments.containsKey(enchantment) || enchantments.getBoolean(enchantment));
        }
        return stack.getEnchantmentLevel(provider.holderOrThrow(enchantment)) > 0 &&
                ((!enchantment.equals(YoyosEnchantments.CRAFTING) && !enchantments.containsKey(enchantment))
                || enchantments.getBoolean(enchantment))
                && checkEnchantmentCompat(stack,enchantment,provider);
    }

    private boolean checkEnchantmentCompat(ItemStack stack, ResourceKey<Enchantment> enchantment, HolderLookup.Provider provider) {
        if (enchantment.equals(YoyosEnchantments.BREAKING)){
            return !isEnchantmentActivate(stack,YoyosEnchantments.CRAFTING,provider);
        }
        if (enchantment.equals(YoyosEnchantments.CRAFTING)){
            return !isEnchantmentActivate(stack,YoyosEnchantments.BREAKING,provider);
        }
        return true;
    }

    public boolean toggleEnchantment(ResourceKey<Enchantment> enchantment,ItemStack stack,HolderLookup.Provider provider){
        boolean newState = !isEnchantmentActivate(stack,enchantment,provider);
        enchantments.removeBoolean(enchantment);
        enchantments.put(enchantment, newState);
        return newState;
    }

    public boolean equals(Object p_331697_) {
        if (this == p_331697_) {
            return true;
        } else {
            boolean flag;
            if (p_331697_ instanceof EnchantmentsState itemenchantments) {
                flag = this.enchantments.equals(itemenchantments.enchantments);
            } else {
                flag = false;
            }

            return flag;
        }
    }

    public int hashCode() {
        int i = this.enchantments.hashCode();
        return 31 * i;
    }
}
