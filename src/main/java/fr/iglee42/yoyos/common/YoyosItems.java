package fr.iglee42.yoyos.common;

import fr.iglee42.yoyos.Yoyos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

public class YoyosItems {

    public static final Item TEST_YOYO = name("test_yoyo");

    private static Item name(String name) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Yoyos.MODID, name));
        if (item == null) {
            throw new RuntimeException(name + " could not be found in " + ForgeRegistries.ITEMS.getRegistryName());
        }
        return item;
    }

    public static void registerItem(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.ITEM)) return;
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"test_yoyo"),()->new YoyoItem(Tiers.DIAMOND).addEntityInteraction(Interaction::attackEntity).addBlockInteraction(Interaction::breakBlocks));
    }
}
