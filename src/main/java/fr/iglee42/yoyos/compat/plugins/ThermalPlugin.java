package fr.iglee42.yoyos.compat.plugins;

import fr.iglee42.yoyos.compat.IYoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPlugin;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fml.ModList;

@YoyoPlugin
public class ThermalPlugin implements IYoyoPlugin {
    @Override
    public String modId() {
        return "thermal";
    }

    @Override
    public void registerYoyos(YoyoPluginHelper helper) {
        helper.registerYoyo("nickel",5.0,8.0,300,5.0,Tiers.IRON,this);
        helper.registerYoyo("tin",5.0,8.0,300,5.0,Tiers.IRON,this);
        helper.registerYoyo("lead",6.0,8.0,300,5.0,Tiers.IRON,this);
        helper.registerYoyo("silver",5.5,8.0,300,5.0,Tiers.IRON,this);
        helper.registerYoyo("bronze",5.0,9.0,500,6.0,Tiers.DIAMOND,this);
        helper.registerYoyo("constantan",5.0,9.0,500,6.0,Tiers.DIAMOND,this);
        helper.registerYoyo("invar",5.0,9.0,500,6.0,Tiers.DIAMOND,this);
        helper.registerYoyo("electrum",6.0,9.0,500,6.0,Tiers.DIAMOND,this);
        helper.registerYoyo("lumium",3.5,10.0,550,6.5,Tiers.NETHERITE,this);
        helper.registerYoyo("signalum",4.0,10.0,550,6.5,Tiers.NETHERITE,this);
        helper.registerYoyo("enderium",4.5,12.0,600,7.0, Tiers.NETHERITE,this);

        if (ModList.get().isLoaded("thermal_integration")){
            helper.registerYoyo("steel",5.5,9.0,500,6.0,Tiers.DIAMOND,this);
            helper.registerYoyo("rose_gold",5.5,10.0,500,5.0,Tiers.IRON,this);
        }
    }
}
