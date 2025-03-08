package fr.iglee42.yoyos.network.payloads;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import static fr.iglee42.yoyos.Yoyos.MODID;

public record HandSyncS2CPayload(Byte hand, ItemStack stack) implements CustomPacketPayload {

    public static final Type<HandSyncS2CPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "hand_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HandSyncS2CPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE, HandSyncS2CPayload::hand,
            ItemStack.OPTIONAL_STREAM_CODEC,HandSyncS2CPayload::stack,
            HandSyncS2CPayload::new
    );

    public HandSyncS2CPayload(InteractionHand hand,ItemStack stack) {
        this((byte) hand.ordinal(),stack);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
