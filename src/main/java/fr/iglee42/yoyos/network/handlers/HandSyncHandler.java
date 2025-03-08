package fr.iglee42.yoyos.network.handlers;

import fr.iglee42.yoyos.common.YoyoEntity;
import fr.iglee42.yoyos.network.payloads.CollectedDropsS2CPayload;
import fr.iglee42.yoyos.network.payloads.HandSyncS2CPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Collections;

public class HandSyncHandler {


    public static final HandSyncHandler INSTANCE = new HandSyncHandler();

    public static HandSyncHandler instance(){
        return INSTANCE;
    }

    public void handle(final HandSyncS2CPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft.getInstance().player.setItemInHand(InteractionHand.values()[payload.hand()],payload.stack());
        });
    }
}
