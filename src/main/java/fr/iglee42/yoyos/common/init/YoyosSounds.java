package fr.iglee42.yoyos.common.init;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.YoyoItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class YoyosSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT,Yoyos.MODID);

    public static DeferredHolder<SoundEvent,SoundEvent> THROW = SOUNDS.register("entity.yoyo.throw", ()->  SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Yoyos.MODID,"entity.yoyo.throw")));

}
