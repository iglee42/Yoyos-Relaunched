package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.compat.tconstruct.PigIronYoyoItem;
import fr.iglee42.yoyos.compat.tconstruct.YoyoModifiableItem;
import fr.iglee42.yoyos.compat.tconstruct.YoyoTF;
import fr.iglee42.yoyos.compat.tconstruct.parts.YoyoCordMaterialStats;
import fr.iglee42.yoyos.compat.tconstruct.parts.YoyoPlateMaterialStats;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.client.model.TinkerItemProperties;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import static slimeknights.tconstruct.library.client.model.tools.ToolModel.registerItemColors;
@YoyoPlugin
public class TconstructPlugin implements IYoyoPlugin {

    protected static final ItemDeferredRegisterExtension ITEMS = new ItemDeferredRegisterExtension(Yoyos.MODID);
    public static final ItemObject<ModifiableItem> YOYO = ITEMS.register("yoyo", () -> new YoyoModifiableItem(new Item.Properties().stacksTo(1), YoyoTF.YOYO_DEFINITION));

    public static final FloatToolStat WEIGHT_STAT = ToolStats.register(new FloatToolStat(new ToolStatId(Yoyos.MODID,"weight"), 0x555555, 2F, 0.1F, 10.0F));
    public static final FloatToolStat DURATION_STAT = ToolStats.register(new FloatToolStat(new ToolStatId(Yoyos.MODID,"duration"), 0x8fce00, 3F, 1F, 3600F));
    public static final FloatToolStat LENGTH_STAT = ToolStats.register(new FloatToolStat(new ToolStatId(Yoyos.MODID,"length"), 0x6fa8dc, 5F, 0.5F, 64F));

    public static final ItemObject<ToolPartItem> YOYO_PLATE = ITEMS.register("yoyo_plate", () -> new ToolPartItem(new Item.Properties(), YoyoPlateMaterialStats.ID));
    public static final ItemObject<ToolPartItem> YOYO_CORD = ITEMS.register("yoyo_cord", () -> new ToolPartItem(new Item.Properties(), YoyoCordMaterialStats.ID));

    public static final CastItemObject YOYO_PLATE_CAST = ITEMS.registerCast(YOYO_PLATE, new Item.Properties());
    @Override
    public String modId() {
        return "tconstruct";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo(new YoyoTier("amethyst_bronze", Tiers.IRON,modId())
                .setWeight(5.0)
                .setLength(8.0)
                .setDuration(300)
                .setDamage(5.5));
        helper.registerYoyo(new YoyoTier("cobalt",Tiers.DIAMOND,modId())
                .setWeight(4.5)
                .setLength(10.0)
                .setDuration(400)
                .setDamage(6.0));
        helper.registerYoyo(new YoyoTier("hepatizon",Tiers.DIAMOND,modId())
                .setWeight(5.0)
                .setLength(10.0)
                .setDuration(500)
                .setDamage(7.0));
        helper.registerYoyo(new YoyoTier("manyullyn",Tiers.NETHERITE,modId())
                .setWeight(6.0)
                .setLength(11.0)
                .setDuration(600)
                .setDamage(7.5));
        try {
            helper.registerYoyo(new YoyoTier("pig_iron",Tiers.IRON,modId())
                    .setWeight(4.5)
                    .setLength(8.0)
                    .setDuration(250)
                    .setDamage(5.5)
                    .setCustomConstructor(PigIronYoyoItem.class.getConstructor(YoyoTier.class)));
        } catch (NoSuchMethodException ignored) {}
        helper.registerYoyo(new YoyoTier("queens_slime",Tiers.DIAMOND,modId())
                .setWeight(6.0)
                .setLength(11.0)
                .setDuration(500)
                .setDamage(7.0));
        helper.registerYoyo(new YoyoTier("rose_gold",Tiers.IRON,modId())
                .setWeight(5.5)
                .setLength(10.0)
                .setDuration(500)
                .setDamage(5.0));
        helper.registerYoyo(new YoyoTier("slimesteel",Tiers.IRON,modId())
                .setWeight(5.5)
                .setLength(9.0)
                .setDuration(350)
                .setDamage(6.0));
    }

    @Override
    public void init(YoyoPluginHelper helper) {
        IYoyoPlugin.super.init(helper);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
    }


    @Override
    public void clientSetup(FMLClientSetupEvent event) {
        IYoyoPlugin.super.clientSetup(event);
        event.enqueueWork(()->{
            TinkerItemProperties.registerToolProperties(YOYO);
            registerItemColors(Minecraft.getInstance().getItemColors(), YOYO);
        });
    }
}
