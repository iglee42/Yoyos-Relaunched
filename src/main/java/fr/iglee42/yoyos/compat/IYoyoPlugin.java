package fr.iglee42.yoyos.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IYoyoPlugin {

    default boolean shouldLoad(){
        return ModList.get().isLoaded(modId());
    };

    String modId();

    void registerYoyos(YoyoPluginHelper helper);

    default void registerItems(RegisterEvent event) {};
    default Map<ResourceLocation, List<String>> addTags() { return new HashMap<>();};
    default void init(YoyoPluginHelper helper){}

    default void clientSetup(FMLClientSetupEvent event){};
}
