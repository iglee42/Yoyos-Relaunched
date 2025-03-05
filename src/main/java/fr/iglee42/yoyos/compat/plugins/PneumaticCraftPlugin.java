package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.compat.pneumaticcraft.PressurizedYoyoItem;
import me.desht.pneumaticcraft.api.lib.Names;
import me.desht.pneumaticcraft.common.util.PneumaticCraftUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@YoyoPlugin
public class PneumaticCraftPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "pneumaticcraft";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        try {
            helper.registerYoyo(new YoyoTier("compressed_iron",Tiers.IRON,modId())
                    .setWeight(5.5)
                    .setLength(7.5)
                    .setDuration(350)
                    .setDamage(5.5)
                    .setCustomItem("ingot_iron_compressed")
                    .setCustomConstructor(PressurizedYoyoItem.class.getConstructor(YoyoTier.class)));
        } catch (NoSuchMethodException ignored) {}
    }
}
