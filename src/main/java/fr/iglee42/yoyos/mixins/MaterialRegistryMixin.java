package fr.iglee42.yoyos.mixins;

import fr.iglee42.yoyos.compat.plugins.TconstructPlugin;
import fr.iglee42.yoyos.compat.tconstruct.parts.YoyoCordMaterialStats;
import fr.iglee42.yoyos.compat.tconstruct.parts.YoyoPlateMaterialStats;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.library.materials.IMaterialRegistry;
import slimeknights.tconstruct.library.materials.MaterialRegistry;

@Mixin(MaterialRegistry.class)
public class MaterialRegistryMixin {

    @Shadow @Final private IMaterialRegistry registry;

    @Inject(method = "<init>()V",at = @At("TAIL"))
    private void yoyos$addNewStatsTypes(CallbackInfo ci){
        registry.registerStatType(YoyoPlateMaterialStats.TYPE);
        registry.registerStatType(YoyoCordMaterialStats.TYPE);
    }
}
