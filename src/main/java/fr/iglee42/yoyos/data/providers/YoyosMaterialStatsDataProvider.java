package fr.iglee42.yoyos.data.providers;

import fr.iglee42.yoyos.compat.tconstruct.parts.YoyoCordMaterialStats;
import fr.iglee42.yoyos.compat.tconstruct.parts.YoyoPlateMaterialStats;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import static net.minecraft.world.item.Tiers.*;

public class YoyosMaterialStatsDataProvider extends AbstractMaterialStatsDataProvider {

  private AbstractMaterialDataProvider prov;
  public YoyosMaterialStatsDataProvider(PackOutput packOutput, AbstractMaterialDataProvider materials) {
    super(packOutput, materials);
    this.prov = materials;
  }

  @Override
  public String getName() {
    return "Yoyos Material Stats";
  }

  @Override
  protected void addMaterialStats() {
    // head order: durability, mining speed, mining level, damage

    // tier 1
    addMaterialStats(MaterialIds.wood,  new YoyoPlateMaterialStats(60, WOOD, 0f,1.1f));
    addMaterialStats(MaterialIds.rock,  new YoyoPlateMaterialStats(130, STONE, 1f,2f));
    addMaterialStats(MaterialIds.flint, new YoyoPlateMaterialStats(85, STONE, 1.25f,1.5f));
    addMaterialStats(MaterialIds.copper,new YoyoPlateMaterialStats(210, STONE, 0.5f,2.5f));
    addMaterialStats(MaterialIds.bone,  new YoyoPlateMaterialStats(100, STONE, 1.25f,1.5f));
    addMaterialStats(MaterialIds.chorus,new YoyoPlateMaterialStats(180, STONE, 1.0f,1.5f));

    // tier 2
    addMaterialStats(MaterialIds.iron,        new YoyoPlateMaterialStats(250,  IRON, 2f,2.5f));
    addMaterialStats(MaterialIds.searedStone, new YoyoPlateMaterialStats(225,  IRON, 1.5f,2f));
    addMaterialStats(MaterialIds.venombone,   new YoyoPlateMaterialStats(175,  IRON, 2.25f,2.25f));
    addMaterialStats(MaterialIds.slimewood,   new YoyoPlateMaterialStats(375,  IRON, 1f,1.2f));
    addMaterialStats(MaterialIds.scorchedStone,new YoyoPlateMaterialStats(120,  IRON, 2.5f,2f));
    addMaterialStats(MaterialIds.necroticBone,new YoyoPlateMaterialStats(125, IRON, 2.25f,2.25f));
    addMaterialStats(MaterialIds.whitestone,  new YoyoPlateMaterialStats(275,  IRON, 1.25f,2f));
    addMaterialStats(MaterialIds.osmium,      new YoyoPlateMaterialStats(500, IRON, 2.0f,2.25f));
    addMaterialStats(MaterialIds.silver,      new YoyoPlateMaterialStats(300, IRON, 2.25f,2.75f));
    addMaterialStats(MaterialIds.lead,        new YoyoPlateMaterialStats(200,  IRON, 1.75f,3f));
    addMaterialStats(MaterialIds.treatedWood, new YoyoPlateMaterialStats(300,  STONE, 1.5f,1.5f));
    //addMaterialStats(MaterialIds.ironwood,    new YoyoPlateMaterialStats(512, 6.5f, IRON, 2f));

    // tier 3
    addMaterialStats(MaterialIds.slimesteel,     new YoyoPlateMaterialStats(1040,  DIAMOND, 2.5f,2.75f));
    addMaterialStats(MaterialIds.amethystBronze, new YoyoPlateMaterialStats(720,  DIAMOND, 1.5f,2.5f));
    addMaterialStats(MaterialIds.nahuatl,        new YoyoPlateMaterialStats(350,  DIAMOND, 3f,2.5f));
    addMaterialStats(MaterialIds.pigIron,        new YoyoPlateMaterialStats(580,  DIAMOND, 2.5f,2.25f));
    addMaterialStats(MaterialIds.roseGold,       new YoyoPlateMaterialStats(175,  GOLD, 1f,2.75f));
    addMaterialStats(MaterialIds.cobalt,         new YoyoPlateMaterialStats(800,  DIAMOND, 2.25f,2.25f));
    addMaterialStats(MaterialIds.steel,          new YoyoPlateMaterialStats(775,  DIAMOND, 2.75f,2.75f));
    addMaterialStats(MaterialIds.bronze,         new YoyoPlateMaterialStats(760,  DIAMOND, 2.25f,2.5f));
    addMaterialStats(MaterialIds.constantan,     new YoyoPlateMaterialStats(675,  DIAMOND, 1.75f,2.5f));
    addMaterialStats(MaterialIds.invar,          new YoyoPlateMaterialStats(630,  DIAMOND, 2.5f,2.5f));
    addMaterialStats(MaterialIds.pewter,         new YoyoPlateMaterialStats(316,  DIAMOND, 3.0f,2.5f));
    addMaterialStats(MaterialIds.necronium,      new YoyoPlateMaterialStats(357,  DIAMOND, 2.75f,3f));
    addMaterialStats(MaterialIds.electrum,       new YoyoPlateMaterialStats(225,  IRON, 1.5f,3f));
    addMaterialStats(MaterialIds.platedSlimewood,new YoyoPlateMaterialStats(595,  DIAMOND, 2.0f,2.5f));
    //addMaterialStats(MaterialIds.steeleaf,       new YoyoPlateMaterialStats(200, 8, IRON, 3));

    // tier 4
    addMaterialStats(MaterialIds.cinderslime, new YoyoPlateMaterialStats(1221,  NETHERITE, 2.25f,3.25f));
    addMaterialStats(MaterialIds.queensSlime,new YoyoPlateMaterialStats(1650,  NETHERITE, 2f,3f));
    addMaterialStats(MaterialIds.hepatizon,  new YoyoPlateMaterialStats(975, NETHERITE, 2.5f,2.5f));
    addMaterialStats(MaterialIds.manyullyn,  new YoyoPlateMaterialStats(1250,  NETHERITE, 3.5f,3f));
    addMaterialStats(MaterialIds.blazingBone,new YoyoPlateMaterialStats(530,  IRON, 3f,3f));
    addMaterialStats(MaterialIds.ancient,    new YoyoPlateMaterialStats(745, NETHERITE, 2.5f,3.5f));
    //addMaterialStats(MaterialIds.knightmetal,new YoyoPlateMaterialStats(512, 8f, NETHERITE, 3.0f));
    //addMaterialStats(MaterialIds.fiery,      new YoyoPlateMaterialStats(1024, 8, NETHERITE, 3.5f));


    // tier 1 - bowstring
    addMaterialStats(MaterialIds.string, new YoyoCordMaterialStats(4,7.5f));
    addMaterialStats(MaterialIds.vine, new YoyoCordMaterialStats(7,8));
    addMaterialStats(MaterialIds.leather, new YoyoCordMaterialStats(5,12.5f));
    // tier 2 - bowstring
    addMaterialStats(MaterialIds.skyslimeVine, new YoyoCordMaterialStats(7,10));
    addMaterialStats(MaterialIds.weepingVine, new YoyoCordMaterialStats(7,10));
    addMaterialStats(MaterialIds.twistingVine,new YoyoCordMaterialStats(7,10));
    addMaterialStats(MaterialIds.slimeskin, new YoyoCordMaterialStats(8,12.5f));

    // tier 3 - bowstring
    addMaterialStats(MaterialIds.darkthread, new YoyoCordMaterialStats(15,20));

    addMaterialStats(MaterialIds.ancientHide, new YoyoCordMaterialStats(20,40));

    addMaterialStats(MaterialIds.enderslimeVine, new YoyoCordMaterialStats(20,30));


    prov.getAllMaterials().forEach(this::addMaterialStats);
  }
}
