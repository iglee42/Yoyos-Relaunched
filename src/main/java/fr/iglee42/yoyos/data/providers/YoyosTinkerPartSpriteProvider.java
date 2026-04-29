package fr.iglee42.yoyos.data.providers;

import fr.iglee42.yoyos.Yoyos;
import net.minecraft.world.item.ArmorItem;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;


public class YoyosTinkerPartSpriteProvider extends AbstractPartSpriteProvider {
  public static final MaterialStatsId WOOD = new MaterialStatsId(TConstruct.MOD_ID, "wood");
  public static final MaterialStatsId SLIMESUIT = new MaterialStatsId(TConstruct.MOD_ID, "slimesuit");
  public static final MaterialStatsId ARMOR_PLATING = new MaterialStatsId(TConstruct.MOD_ID, "armor_plating");
  public static final MaterialStatsId ARMOR_MAILLE = new MaterialStatsId(TConstruct.MOD_ID, "armor_maille");
  public static final MaterialStatsId ARMOR_CUIRASS = new MaterialStatsId(TConstruct.MOD_ID, "armor_cuirass");

  public YoyosTinkerPartSpriteProvider() {
    super(Yoyos.MODID);
  }

  @Override
  public String getName() {
    return "Tinkers' Construct Parts";
  }

  @Override
  protected void addAllSpites() {


    buildTool("yoyo").addBreakableHead("front").addHead("back").addBreakableHead("cord").addHead("parts/plate").addHead("parts/cord");
  }
}