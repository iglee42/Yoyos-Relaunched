package fr.iglee42.yoyos.data;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.data.providers.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.tools.data.material.MaterialDataProvider;
import slimeknights.tconstruct.tools.data.sprite.TinkerMaterialSpriteProvider;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = Yoyos.MODID)
public class YoyosDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput output = event.getGenerator().getPackOutput();

        //generator.addProvider(event.includeServer(),new YoyosRecipesGenerator(output));
        //generator.addProvider(event.includeClient(),new YoyosItemModelsGenerator(output,helper));
        //generator.addProvider(event.includeClient(),new YoyosLangGenerator(output));
        generator.addProvider(event.includeServer(),new YoyosToolDefinitionGenerator(output));
        generator.addProvider(event.includeServer(),new YoyosStationLayoutsGenerator(output));
        generator.addProvider(event.includeClient(),new YoyosToolsModelGenerator(output,helper));
        var blockTagsProvider = new YoyosBlocksTagsProvider(output,event.getLookupProvider(),helper);
        generator.addProvider(event.includeServer(),blockTagsProvider);
        generator.addProvider(event.includeServer(),new YoyosToolTagsProvider(output,event.getLookupProvider(),blockTagsProvider.contentsGetter(),helper));

        var materialsProvider = new MaterialDataProvider(output);
        generator.addProvider(event.includeServer(),new YoyosMaterialStatsDataProvider(output,materialsProvider));
        generator.addProvider(event.includeClient(),new YoyosItemModelsGenerator(output,helper));
        generator.addProvider(event.includeServer(),new YoyosRecipesGenerator(output));
        var partProvider = new YoyosTinkerPartSpriteProvider();
        generator.addProvider(event.includeClient(),new MaterialPartTextureGenerator(output,helper,partProvider,new TinkerMaterialSpriteProvider()));
    }
}
