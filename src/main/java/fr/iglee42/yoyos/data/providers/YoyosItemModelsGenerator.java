package fr.iglee42.yoyos.data.providers;

import fr.iglee42.yoyos.Yoyos;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class YoyosItemModelsGenerator extends ItemModelProvider {
    public YoyosItemModelsGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Yoyos.MODID, existingFileHelper);
    }

    @Override
    public void registerModels() {

        ForgeRegistries.ITEMS.getKeys().stream().filter(rs->rs.getNamespace().equals(modid)).forEach(rs->{
            if (!rs.getPath().endsWith("_yoyo")) basicItem(rs);
            withExistingParent(rs.toString(),"item/handheld").texture("layer0",modid+":item/"+rs.getPath().replace("_yoyo",""));
        });

    }
}
