package fr.iglee42.yoyos.common.init;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.Interaction;
import fr.iglee42.yoyos.common.YoyoItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
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



    private static Item name(String name) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Yoyos.MODID, name));
        if (item == null) {
            throw new RuntimeException(name + " could not be found in " + ForgeRegistries.ITEMS.getRegistryName());
        }
        return item;
    }

    public static void registerItem(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.ITEM)) return;
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"cord"),()->new Item(new Item.Properties()));
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"diamond_yoyo"),()->new YoyoItem(Tiers.DIAMOND).addEntityInteraction(Interaction::attackEntity,Interaction::collectItem).addBlockInteraction(Interaction::breakBlocks,Interaction::craftWithBlock));
    }
}
