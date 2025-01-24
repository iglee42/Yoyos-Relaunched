package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegisterEvent;

@YoyoPlugin
public class AdAstraPlugin implements IYoyoPlugin {

    @Override
    public String modId() {
        return "ad_astra";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo("desh",4.5,10.0,400,5.5,Tiers.IRON,this);
        helper.registerYoyo("ostrum",5.0,11.0,500,6.0,Tiers.DIAMOND,this);
        helper.registerYoyo("calorite",5.5,12.0,550,6.5,Tiers.NETHERITE,this);
        helper.registerYoyo("steel",5.5,9.0,500,6.0,Tiers.DIAMOND,this);

        helper.setCustomCord("desh","cheese");
        helper.setCustomCord("ostrum","cheese");
        helper.setCustomCord("calorite","cheese");
    }

    @Override
    public void registerItems(RegisterEvent event) {
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"cheese_string"),()-> new Item(new Item.Properties()));
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"cheese_cord"),()-> new Item(new Item.Properties()));
    }
}
