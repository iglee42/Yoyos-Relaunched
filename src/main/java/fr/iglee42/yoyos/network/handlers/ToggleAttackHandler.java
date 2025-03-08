package fr.iglee42.yoyos.network.handlers;

import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.network.payloads.ToggleAttackC2SPayload;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ToggleAttackHandler {


    public static final ToggleAttackHandler INSTANCE = new ToggleAttackHandler();

    public static ToggleAttackHandler instance(){
        return INSTANCE;
    }

    public void handle(final ToggleAttackC2SPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            InteractionHand hand = InteractionHand.values()[payload.hand()];
            YoyoItem.toggleAttack(context.player().getItemInHand(hand));
        });
    }
}
