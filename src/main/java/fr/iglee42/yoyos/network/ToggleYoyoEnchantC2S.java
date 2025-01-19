package fr.iglee42.yoyos.network;

import fr.iglee42.yoyos.common.Interaction;
import fr.iglee42.yoyos.common.YoyoEntity;
import fr.iglee42.yoyos.common.YoyoItem;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collections;
import java.util.function.Supplier;

public class ToggleYoyoEnchantC2S {

    private final ResourceLocation enchant;
    private final InteractionHand hand;


    public ToggleYoyoEnchantC2S(ResourceLocation enchant,InteractionHand hand) {
        this.enchant = enchant;
        this.hand = hand;
    }

    public ToggleYoyoEnchantC2S(FriendlyByteBuf buf){
       enchant = buf.readResourceLocation();
       hand = InteractionHand.values()[buf.readByte()];
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeResourceLocation(enchant);
        buf.writeByte(hand.ordinal());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            YoyoItem.toggleEnchant(ctx.get().getSender().getItemInHand(hand),enchant);
        });
        ctx.get().setPacketHandled(true);
    }
}
