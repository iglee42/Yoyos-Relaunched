package fr.iglee42.yoyos.network.handlers;

import fr.iglee42.yoyos.common.YoyoEntity;
import fr.iglee42.yoyos.network.payloads.CollectedDropsS2CPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Collections;

public class CollectedDropsSyncHandler {


    public static final CollectedDropsSyncHandler INSTANCE = new CollectedDropsSyncHandler();

    public static CollectedDropsSyncHandler instance(){
        return INSTANCE;
    }

    public void handle(final CollectedDropsS2CPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Entity e = Minecraft.getInstance().level.getEntity(payload.yoyoId());
            if (e instanceof YoyoEntity yoyo){
                yoyo.getCollectedDrops().clear();
                Collections.addAll(yoyo.getCollectedDrops(),payload.drops());
            }
        });
    }
}
