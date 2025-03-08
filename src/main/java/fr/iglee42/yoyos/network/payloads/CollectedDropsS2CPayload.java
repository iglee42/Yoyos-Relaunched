package fr.iglee42.yoyos.network.payloads;

import fr.iglee42.yoyos.common.YoyoEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.UUID;

import static fr.iglee42.yoyos.Yoyos.MODID;

public record CollectedDropsS2CPayload(
        int yoyoId,
        ItemStack[] drops
) implements CustomPacketPayload {

    public static final Type<CollectedDropsS2CPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "collected_drop_sync"));


    public CollectedDropsS2CPayload(YoyoEntity entity){
        this(entity.getId(),entity.getCollectedDrops().toArray(new ItemStack[]{}));
    }

    private static final StreamCodec<RegistryFriendlyByteBuf,ItemStack[]> STACKS = StreamCodec.of(
            (buf,stacks)->{
                buf.writeInt(stacks.length);
                for (ItemStack drop : stacks) {
                    ItemStack.OPTIONAL_STREAM_CODEC.encode(buf,drop);
                }
            },
            (buf)->{
                int size = buf.readInt();

                ItemStack[] drops = new ItemStack[size];

                for (int i = 0; i < size; i++) {
                    drops[i] = ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);
                }
                return drops;
            }
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CollectedDropsS2CPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, CollectedDropsS2CPayload::yoyoId,
            STACKS,CollectedDropsS2CPayload::drops,
            CollectedDropsS2CPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


}
