package fr.iglee42.yoyos.data.providers;

import fr.iglee42.yoyos.compat.plugins.TconstructPlugin;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;
import slimeknights.tconstruct.tools.TinkerToolParts;


public class YoyosStationLayoutsGenerator extends AbstractStationSlotLayoutProvider {
    public YoyosStationLayoutsGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addLayouts() {
        defineModifiable(TconstructPlugin.YOYO)
                .sortIndex(SORT_RANGED)
                .addInputItem(TconstructPlugin.YOYO_PLATE,  25, 20)
                .addInputItem(TconstructPlugin.YOYO_PLATE,  50, 48)
                .addInputItem(TinkerToolParts.toughHandle,  44, 29)
                .addInputItem(TconstructPlugin.YOYO_CORD, 21, 52)
                .build();
    }

    @Override
    public String getName() {
        return "Yoyos Station Layouts Data Generator";
    }
}
