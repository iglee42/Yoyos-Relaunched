package fr.iglee42.yoyos.network;

import fr.iglee42.yoyos.network.handlers.CollectedDropsSyncHandler;
import fr.iglee42.yoyos.network.handlers.HandSyncHandler;
import fr.iglee42.yoyos.network.handlers.ToggleAttackHandler;
import fr.iglee42.yoyos.network.handlers.ToggleEnchantmentHandler;
import fr.iglee42.yoyos.network.payloads.CollectedDropsS2CPayload;
import fr.iglee42.yoyos.network.payloads.HandSyncS2CPayload;
import fr.iglee42.yoyos.network.payloads.ToggleAttackC2SPayload;
import fr.iglee42.yoyos.network.payloads.ToggleEnchantmentC2SPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static fr.iglee42.yoyos.Yoyos.MODID;

@EventBusSubscriber(modid = MODID,bus = EventBusSubscriber.Bus.MOD)
public class YoyosNetwork {
    @SubscribeEvent
    public static void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MODID);

        registrar.playToServer(ToggleAttackC2SPayload.TYPE, ToggleAttackC2SPayload.STREAM_CODEC, ToggleAttackHandler.instance()::handle);
        registrar.playToServer(ToggleEnchantmentC2SPayload.TYPE, ToggleEnchantmentC2SPayload.STREAM_CODEC, ToggleEnchantmentHandler.instance()::handle);
        registrar.playToClient(CollectedDropsS2CPayload.TYPE, CollectedDropsS2CPayload.STREAM_CODEC, CollectedDropsSyncHandler.instance()::handle);
        registrar.playToClient(HandSyncS2CPayload.TYPE, HandSyncS2CPayload.STREAM_CODEC, HandSyncHandler.instance()::handle);

    }
}
