package fr.iglee42.yoyos.mixins;

import fr.iglee42.yoyos.compat.plugins.TconstructPlugin;
import fr.iglee42.yoyos.compat.tconstruct.YoyoModifiableItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

@Mixin(value = TooltipUtil.class,remap = false)
public class TooltipUtilMixin {

    @Inject(method = "getDefaultStats",at= @At(value = "INVOKE", target = "Lslimeknights/tconstruct/library/tools/helper/TooltipBuilder;addDurability()Lslimeknights/tconstruct/library/tools/helper/TooltipBuilder;",shift = At.Shift.AFTER),locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void yoyos$addYoyosStats(IToolStackView tool, Player player, List<Component> tooltip, TooltipKey key, TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir, TooltipBuilder builder){
        if (tool.getItem() instanceof YoyoModifiableItem) {
            builder.addTier();
            builder.addOptional(TconstructPlugin.WEIGHT_STAT);
            builder.addOptional(TconstructPlugin.LENGTH_STAT);
            builder.addOptional(TconstructPlugin.DURATION_STAT);
            builder.addOptional(ToolStats.PROJECTILE_DAMAGE);
        }
    }

}
