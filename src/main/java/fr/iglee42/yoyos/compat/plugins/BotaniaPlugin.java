package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.compat.botania.ElementiumYoyoItem;
import fr.iglee42.yoyos.compat.botania.ManaYoyoItem;
import fr.iglee42.yoyos.compat.botania.TerrasteelYoyoItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegisterEvent;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@YoyoPlugin
public class BotaniaPlugin implements IYoyoPlugin {
    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo("manasteel",4.5,9.0,400,5.5,Tiers.IRON,this);
        helper.registerYoyo("elementium",5.0,10.0,500,6.0,Tiers.DIAMOND,this);
        helper.registerYoyo("terrasteel",6.0,11.0,650,7.0,Tiers.NETHERITE,this);
        helper.registerYoyo("gaia_spirit",5.0,12.0,700,8.0,Tiers.NETHERITE,this);


        if (ModList.get().isLoaded("mythicbotany")){
            helper.registerYoyo("alfsteel",6.0,18.0,800,9.0,Tiers.NETHERITE,this);
            YoyoPluginHelper.YOYO_BY_MODS.put("alfsteel","mythicbotany");
        }

        helper.setCustomCord("manasteel","mana");
        helper.setCustomCord("elementium","mana");
        helper.setCustomCord("terrasteel","mana");
        helper.setCustomCord("gaia_spirit","mana");
        if (ModList.get().isLoaded("mythicbotany"))helper.setCustomCord("alfsteel","mana");

        helper.setCustomItem("gaia_spirit","gaia_ingot");

        try {
            helper.setCustomConstructor("manasteel", ManaYoyoItem.class.getConstructor(YoyoTier.class));
            helper.setCustomConstructor("terrasteel", TerrasteelYoyoItem.class.getConstructor(YoyoTier.class));
            helper.setCustomConstructor("elementium", ElementiumYoyoItem.class.getConstructor(YoyoTier.class));
        } catch (NoSuchMethodException ignored) {}
    }

    @Override
    public String modId() {
        return "botania";
    }

    @Override
    public void registerItems(RegisterEvent event) {
        event.register(Registries.ITEM,new ResourceLocation(Yoyos.MODID,"mana_cord"),()-> new Item(new Item.Properties()));
    }

    @Override
    public Map<ResourceLocation, List<String>> addTags() {
        Map<ResourceLocation,List<String>> tags = new HashMap<>();
        tags.put(BotaniaTags.Items.MANA_USING_ITEMS.location(),List.of("yoyos:manasteel_yoyo","yoyos:terrasteel_yoyo"));
        return tags;
    }
}
