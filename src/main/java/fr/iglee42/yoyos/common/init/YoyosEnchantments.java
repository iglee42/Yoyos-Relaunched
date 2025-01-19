package fr.iglee42.yoyos.common.init;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.YoyoItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

public class YoyosEnchantments {

    public static final EnchantmentCategory YOYOS_CATEGORY = EnchantmentCategory.create("yoyos",i->i instanceof YoyoItem);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS,Yoyos.MODID);
    public static RegistryObject<Enchantment> COLLECTING = ENCHANTMENTS.register("collecting", ()-> new Enchantment(Enchantment.Rarity.UNCOMMON,YOYOS_CATEGORY,new EquipmentSlot[]{EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND}) {
        @Override
        public int getMaxLevel() {
            return 5;
        }
    });
    public static RegistryObject<Enchantment> BREAKING = ENCHANTMENTS.register("breaking", ()-> new Enchantment(Enchantment.Rarity.UNCOMMON,YOYOS_CATEGORY,new EquipmentSlot[]{EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND}) {});
    public static RegistryObject<Enchantment> CRAFTING = ENCHANTMENTS.register("crafting", ()-> new Enchantment(Enchantment.Rarity.RARE,YOYOS_CATEGORY,new EquipmentSlot[]{EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND}) {});

}
