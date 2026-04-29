package fr.iglee42.yoyos.data.providers;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.compat.plugins.TconstructPlugin;
import fr.iglee42.yoyos.compat.tconstruct.YoyoTF;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.materials.RandomMaterial;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.tools.definition.module.build.SetStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.DefaultMaterialsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.PartStatsModule;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import static slimeknights.tconstruct.tools.TinkerToolParts.toughHandle;

public class YoyosModifierGenerator extends AbstractModifierProvider {
    public YoyosModifierGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addModifiers() {
        buildModifier(TconstructPlugin.ITEM_COLLECTING_MODIFIER);
    }

    @Override
    public String getName() {
        return "Yoyos Modifiers Data Generator";
    }
}
