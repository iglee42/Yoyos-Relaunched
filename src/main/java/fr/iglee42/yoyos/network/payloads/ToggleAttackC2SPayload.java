package fr.iglee42.yoyos.network.payloads;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;

import static fr.iglee42.yoyos.Yoyos.MODID;

public record ToggleAttackC2SPayload(Byte hand) implements CustomPacketPayload {

    public static final Type<ToggleAttackC2SPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "toggle_attack"));

    public static final StreamCodec<RegistryFriendlyByteBuf,ToggleAttackC2SPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE, ToggleAttackC2SPayload::hand,
            ToggleAttackC2SPayload::new
    );

    public ToggleAttackC2SPayload(InteractionHand hand) {
        this((byte) hand.ordinal());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
