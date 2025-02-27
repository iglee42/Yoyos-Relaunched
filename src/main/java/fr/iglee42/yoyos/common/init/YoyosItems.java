package fr.iglee42.yoyos.common.init;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.Interaction;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyosTiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

public class YoyosItems {


    public static final Item CORD = name("cord");
    public static final Item DIAMOND_YOYO = name("diamond_yoyo");

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,Yoyos.MODID);
    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab",()->CreativeModeTab.builder()
            .icon(()->new ItemStack(name("cord")))
            .title(Component.translatable("yoyos.creativeTab"))
            .build());



    public static Item name(String name) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Yoyos.MODID, name));
        if (item == null) {
            throw new RuntimeException(name + " could not be found in " + ForgeRegistries.ITEMS.getRegistryName());
        }
        return item;
    }

    public static void registerItem(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.ITEM)) return;
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"cord"),()->new Item(new Item.Properties()));
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"wooden_yoyo"),()->new YoyoItem(YoyosTiers.WOODEN).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock));
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"stone_yoyo"),()->new YoyoItem(YoyosTiers.STONE).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock));
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"copper_yoyo"),()->new YoyoItem(YoyosTiers.COPPER).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock));
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"iron_yoyo"),()->new YoyoItem(YoyosTiers.IRON).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock));
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"golden_yoyo"),()->new YoyoItem(YoyosTiers.GOLD).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock));
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"diamond_yoyo"),()->new YoyoItem(YoyosTiers.DIAMOND).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock));
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"netherite_yoyo"),()->new YoyoItem(YoyosTiers.NETHERITE).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock));
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"creative_yoyo"),()->new YoyoItem(YoyosTiers.CREATIVE).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock));
    }
}
