package fr.iglee42.yoyos.data.providers;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.compat.plugins.TconstructPlugin;
import fr.iglee42.yoyos.compat.tconstruct.YoyoTF;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.ToolActions;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.materials.RandomMaterial;
import slimeknights.tconstruct.library.tools.definition.module.aoe.BoxAOEIterator;
import slimeknights.tconstruct.library.tools.definition.module.aoe.IBoxExpansion;
import slimeknights.tconstruct.library.tools.definition.module.build.SetStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolActionsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolTraitsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.DefaultMaterialsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.PartStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.mining.IsEffectiveModule;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.data.ModifierIds;

import static slimeknights.tconstruct.tools.TinkerToolParts.adzeHead;
import static slimeknights.tconstruct.tools.TinkerToolParts.bowGrip;
import static slimeknights.tconstruct.tools.TinkerToolParts.bowLimb;
import static slimeknights.tconstruct.tools.TinkerToolParts.bowstring;
import static slimeknights.tconstruct.tools.TinkerToolParts.broadAxeHead;
import static slimeknights.tconstruct.tools.TinkerToolParts.broadBlade;
import static slimeknights.tconstruct.tools.TinkerToolParts.hammerHead;
import static slimeknights.tconstruct.tools.TinkerToolParts.largePlate;
import static slimeknights.tconstruct.tools.TinkerToolParts.pickHead;
import static slimeknights.tconstruct.tools.TinkerToolParts.smallAxeHead;
import static slimeknights.tconstruct.tools.TinkerToolParts.smallBlade;
import static slimeknights.tconstruct.tools.TinkerToolParts.toolBinding;
import static slimeknights.tconstruct.tools.TinkerToolParts.toolHandle;
import static slimeknights.tconstruct.tools.TinkerToolParts.toughBinding;
import static slimeknights.tconstruct.tools.TinkerToolParts.toughHandle;

public class YoyosToolDefinitionGenerator extends AbstractToolDefinitionDataProvider {
    public YoyosToolDefinitionGenerator(PackOutput packOutput) {
        super(packOutput, Yoyos.MODID);
    }

    @Override
    protected void addToolDefinitions() {
        RandomMaterial tier1Material = RandomMaterial.random().tier(1).build();
        DefaultMaterialsModule defaultFourParts = DefaultMaterialsModule.builder().material(tier1Material, tier1Material, tier1Material, tier1Material).build();
        define(YoyoTF.YOYO_DEFINITION)
                // parts
                .module(PartStatsModule.parts()
                        .part(TconstructPlugin.YOYO_PLATE)
                        .part(TconstructPlugin.YOYO_PLATE)
                        .part(toughHandle)
                        .part(TconstructPlugin.YOYO_CORD)
                        .build())
                .module(defaultFourParts)
                // stats
                .module(new SetStatsModule(StatsNBT.builder()
                        .set(ToolStats.ATTACK_DAMAGE, 0.5f)
                        .set(ToolStats.ATTACK_SPEED, 1.2f)
                        .build()))
                .smallToolStartingSlots()
                // traits
                // harvest
                .build();
    }

    @Override
    public String getName() {
        return "Yoyos Tool Definition Data Generator";
    }
}
