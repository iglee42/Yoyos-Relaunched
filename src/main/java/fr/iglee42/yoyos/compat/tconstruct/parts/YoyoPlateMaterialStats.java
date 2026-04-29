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
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.json.TinkerLoadables;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public record YoyoPlateMaterialStats(int durability, Tier tier, float attack, float weight) implements IRepairableMaterialStats {
  public static final MaterialStatsId ID = new MaterialStatsId(ResourceLocation.fromNamespaceAndPath(Yoyos.MODID,"yoyo_plate"));
  public static final MaterialStatType<YoyoPlateMaterialStats> TYPE = new MaterialStatType<>(ID, new YoyoPlateMaterialStats(1, Tiers.WOOD, 1f,2f), RecordLoadable.create(
    IRepairableMaterialStats.DURABILITY_FIELD,
    TinkerLoadables.TIER.defaultField("mining_tier", Tiers.WOOD, true, YoyoPlateMaterialStats::tier),
    FloatLoadable.FROM_ZERO.defaultField("attack_damage", 1f, true, YoyoPlateMaterialStats::attack),
    FloatLoadable.FROM_ZERO.defaultField("weight", 2f, true, YoyoPlateMaterialStats::weight),
    YoyoPlateMaterialStats::new));

  // tooltip descriptions
  private static final List<Component> DESCRIPTION = ImmutableList.of(ToolStats.DURABILITY.getDescription(), ToolStats.HARVEST_TIER.getDescription(), ToolStats.ATTACK_DAMAGE.getDescription(), TconstructPlugin.WEIGHT_STAT.getDescription());

  @Override
  public MaterialStatType<?> getType() {
    return TYPE;
  }

  @Override
  public List<Component> getLocalizedInfo() {
    List<Component> info = Lists.newArrayList();
    info.add(ToolStats.DURABILITY.formatValue(this.durability));
    info.add(ToolStats.HARVEST_TIER.formatValue(this.tier));
    info.add(ToolStats.ATTACK_DAMAGE.formatValue(this.attack));
    info.add(TconstructPlugin.WEIGHT_STAT.formatValue(this.weight));
    return info;
  }

  @Override
  public List<Component> getLocalizedDescriptions() {
    return DESCRIPTION;
  }

  @Override
  public void apply(ModifierStatsBuilder builder, float scale) {
    // update for floats cancels out the base stats the first time used, makes the behavior more predictable between this and the stats module
    ToolStats.DURABILITY.update(builder, durability * scale);
    ToolStats.ATTACK_DAMAGE.update(builder, attack * scale);
    TconstructPlugin.WEIGHT_STAT.update(builder,weight * scale);
    // no need to scale tier, we just take the max across everything
    ToolStats.HARVEST_TIER.update(builder, tier);
  }
}