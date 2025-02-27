package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;
import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import fr.iglee42.yoyos.compat.botania.ElementiumYoyoItem;
import fr.iglee42.yoyos.compat.botania.GaiaYoyoItem;
import fr.iglee42.yoyos.compat.botania.ManaYoyoItem;
import fr.iglee42.yoyos.compat.botania.TerrasteelYoyoItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegisterEvent;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@YoyoPlugin
public class BotaniaPlugin implements IYoyoPlugin {
    @Override
    public void registerYoyos(YoyoPluginHelper helper) {

        try {
            helper.registerYoyo(new YoyoTier("manasteel", Tiers.IRON, modId())
                    .setWeight(4.5)
                    .setLength(9.0)
                    .setDuration(400)
                    .setDamage(5.5)
                    .setCustomCord("mana")
                    .setCustomConstructor(ManaYoyoItem.class.getConstructor(YoyoTier.class)));
            helper.registerYoyo(new YoyoTier("elementium", Tiers.DIAMOND, modId())
                    .setWeight(5.0)
                    .setLength(10.0)
                    .setDuration(500)
                    .setDamage(6.0)
                    .setCustomCord("mana")
                    .setCustomConstructor(ElementiumYoyoItem.class.getConstructor(YoyoTier.class)));
            helper.registerYoyo(new YoyoTier("terrasteel", Tiers.NETHERITE, modId())
                    .setWeight(6.0)
                    .setLength(11.0)
                    .setDuration(650)
                    .setDamage(7.0)
                    .setCustomCord("mana")
                    .setCustomConstructor(TerrasteelYoyoItem.class.getConstructor(YoyoTier.class)));
            helper.registerYoyo(new YoyoTier("gaia_spirit", Tiers.NETHERITE, modId())
                    .setWeight(5.0)
                    .setLength(12.0)
                    .setDuration(700)
                    .setDamage(8.0)
                    .setCustomCord("mana")
                    .setCustomConstructor(GaiaYoyoItem.class.getConstructor(YoyoTier.class))
                    .setCustomItem("gaia_ingot"));

            if (ModList.get().isLoaded("mythicbotany")){
                helper.registerYoyo(new YoyoTier("alfsteel",Tiers.NETHERITE,modId())
                        .setWeight(6.0)
                        .setLength(18.0)
                        .setDuration(800)
                        .setDamage(9.0)
                        .setMod("mythicbotany")
                        .setCustomCord("mana")
                        .setCustomConstructor(TerrasteelYoyoItem.class.getConstructor(YoyoTier.class)));
            }

        }catch (NoSuchMethodException ignored){}
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
        List<String> manaItems = new ArrayList<>(List.of("yoyos:manasteel_yoyo", "yoyos:terrasteel_yoyo", "yoyos:elementium_yoyo", "yoyos:gaia_spirit_yoyo"));
        if (ModList.get().isLoaded("mythicbotany")) manaItems.add("yoyos:alfsteel_yoyo");
        tags.put(BotaniaTags.Items.MANA_USING_ITEMS.location(),manaItems);
        return tags;
    }
}
