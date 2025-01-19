package fr.iglee42.yoyos.data;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.data.providers.YoyosItemModelsGenerators;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = Yoyos.MODID)
public class YoyosDataGenerator {

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput output = event.getGenerator().getPackOutput();

        generator.addProvider(event.includeClient(),new YoyosItemModelsGenerators(output,helper));
    }
}
