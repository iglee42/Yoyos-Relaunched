package fr.iglee42.yoyos.network.handlers;

import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.network.payloads.ToggleAttackC2SPayload;
import fr.iglee42.yoyos.network.payloads.ToggleEnchantmentC2SPayload;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ToggleEnchantmentHandler {


    public static final ToggleEnchantmentHandler INSTANCE = new ToggleEnchantmentHandler();

    public static ToggleEnchantmentHandler instance(){
        return INSTANCE;
    }

    public void handle(final ToggleEnchantmentC2SPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            InteractionHand hand = InteractionHand.values()[payload.hand()];
            HolderLookup.RegistryLookup<Enchantment> enchs = context.player().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            YoyoItem.toggleEnchant(context.player().getItemInHand(hand),ResourceKey.create(Registries.ENCHANTMENT,payload.enchantment()),context.player().registryAccess(),payload.player());
        });
    }
}
