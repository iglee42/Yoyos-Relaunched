package fr.iglee42.yoyos.network.payloads;

import net.minecraft.client.Minecraft;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;

import java.util.UUID;

import static fr.iglee42.yoyos.Yoyos.MODID;

public record ToggleEnchantmentC2SPayload(Byte hand, ResourceLocation enchantment, UUID player) implements CustomPacketPayload {

    public static final Type<ToggleEnchantmentC2SPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "toggle_enchantment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleEnchantmentC2SPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE, ToggleEnchantmentC2SPayload::hand,
            ResourceLocation.STREAM_CODEC,ToggleEnchantmentC2SPayload::enchantment,
            UUIDUtil.STREAM_CODEC,ToggleEnchantmentC2SPayload::player,
            ToggleEnchantmentC2SPayload::new
    );

    public ToggleEnchantmentC2SPayload(InteractionHand hand,ResourceLocation enchantment) {
        this((byte) hand.ordinal(),enchantment, Minecraft.getInstance().player.getUUID());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
