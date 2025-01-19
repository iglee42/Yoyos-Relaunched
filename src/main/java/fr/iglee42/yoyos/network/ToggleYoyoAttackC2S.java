package fr.iglee42.yoyos.network;

import fr.iglee42.yoyos.common.YoyoItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleYoyoAttackC2S {

    private final InteractionHand hand;


    public ToggleYoyoAttackC2S(InteractionHand hand) {
        this.hand = hand;
    }

    public ToggleYoyoAttackC2S(FriendlyByteBuf buf){
       hand = InteractionHand.values()[buf.readByte()];
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeByte(hand.ordinal());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            YoyoItem.toggleAttack(ctx.get().getSender().getItemInHand(hand));
        });
        ctx.get().setPacketHandled(true);
    }
}
