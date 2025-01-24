package fr.iglee42.yoyos.compat;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegisterEvent;

public interface IYoyoPlugin {

    default boolean shouldLoad(){
        return ModList.get().isLoaded(modId());
    };

    String modId();

    void registerYoyos(YoyoPluginHelper helper);

    default void registerItems(RegisterEvent event) {};

}
