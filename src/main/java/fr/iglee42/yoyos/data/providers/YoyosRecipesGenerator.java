package fr.iglee42.yoyos.data.providers;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.init.YoyosItems;
import fr.iglee42.yoyos.common.YoyosTiers;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class YoyosRecipesGenerator extends RecipeProvider {
    public YoyosRecipesGenerator(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        YoyosTiers.TIERS.forEach(t-> {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,()-> ForgeRegistries.ITEMS.getValue(new ResourceLocation(Yoyos.MODID,t.getName().toLowerCase()+"_yoyo")))
                    .pattern(" I ")
                    .pattern("ISI")
                    .pattern("CI ")
                    .define('I',t.getTier().getRepairIngredient())
                    .define('C', ()->YoyosItems.CORD)
                    .define('S', Items.STICK)
                    .unlockedBy("unlock", InventoryChangeTrigger.TriggerInstance.hasItems(t.getTier().getRepairIngredient().getItems()[0].getItem()))
                    .save(consumer);

        });
    }
}
