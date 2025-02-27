package fr.iglee42.yoyos.compat.plugins;

import earth.terrarium.adastra.api.events.AdAstraEvents;
import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.YoyoEntity;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@YoyoPlugin
public class AdAstraPlugin implements IYoyoPlugin {

    @Override
    public String modId() {
        return "ad_astra";
    }

    @Override
    public void init(YoyoPluginHelper helper) {
        AdAstraEvents.EntityGravityEvent.register(this::yoyoGravity);
    }

    private float yoyoGravity(Entity entity, float gravity) {
        if (!(entity instanceof YoyoEntity yoyo)) return gravity;
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(yoyo.getYoyoStack().getItem());
        if (id != null && (id.getPath().equals("desh_yoyo") || id.getPath().equals("ostrum_yoyo") || id.getPath().equals("calorite_yoyo"))){
            if (gravity != 1.0f) return 1.0f;
        }
        return gravity;
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo(new YoyoTier("steel",Tiers.IRON,modId())
                .setWeight(5.5)
                .setLength(9.0)
                .setDuration(500)
                .setDamage(6.0));
        helper.registerYoyo(new YoyoTier("desh",Tiers.IRON,modId())
                .setWeight(4.5)
                .setLength(10.0)
                .setDuration(400)
                .setDamage(5.5)
                .setCustomCord("cheese"));
        helper.registerYoyo(new YoyoTier("ostrum",Tiers.DIAMOND,modId())
                .setWeight(5.0)
                .setLength(11.0)
                .setDuration(500)
                .setDamage(6.0)
                .setCustomCord("cheese"));
        helper.registerYoyo(new YoyoTier("calorite",Tiers.NETHERITE,modId())
                .setWeight(5.5)
                .setLength(12.0)
                .setDuration(550)
                .setDamage(6.5)
                .setCustomCord("cheese"));
    }

    @Override
    public void registerItems(RegisterEvent event) {
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"cheese_string"),()-> new Item(new Item.Properties()));
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"cheese_cord"),()-> new Item(new Item.Properties()));
    }

}
