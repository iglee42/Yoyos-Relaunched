package fr.iglee42.yoyos.compat.tconstruct.parts;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.compat.plugins.TconstructPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.json.TinkerLoadables;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public record YoyoCordMaterialStats( float length, float duration) implements IMaterialStats {
  public static final MaterialStatsId ID = new MaterialStatsId(ResourceLocation.fromNamespaceAndPath(Yoyos.MODID,"yoyo_cord"));
  public static final MaterialStatType<YoyoCordMaterialStats> TYPE = new MaterialStatType<>(ID, new YoyoCordMaterialStats(5f,3f), RecordLoadable.create(
    FloatLoadable.FROM_ZERO.defaultField("length", 5f, true, YoyoCordMaterialStats::length),
    FloatLoadable.FROM_ZERO.defaultField("duration", 3f, true, YoyoCordMaterialStats::duration),
    YoyoCordMaterialStats::new));

  // tooltip descriptions
  private static final List<Component> DESCRIPTION = ImmutableList.of(  TconstructPlugin.LENGTH_STAT.getDescription(), TconstructPlugin.DURATION_STAT.getDescription());

  @Override
  public MaterialStatType<?> getType() {
    return TYPE;
  }

  @Override
  public List<Component> getLocalizedInfo() {
    List<Component> info = Lists.newArrayList();
    info.add(TconstructPlugin.LENGTH_STAT.formatValue(this.length));
    info.add(TconstructPlugin.DURATION_STAT.formatValue(this.duration));
    return info;
  }

  @Override
  public List<Component> getLocalizedDescriptions() {
    return DESCRIPTION;
  }

  @Override
  public void apply(ModifierStatsBuilder builder, float scale) {
    TconstructPlugin.LENGTH_STAT.update(builder, length * scale);
    TconstructPlugin.DURATION_STAT.update(builder,duration * scale);
  }
}