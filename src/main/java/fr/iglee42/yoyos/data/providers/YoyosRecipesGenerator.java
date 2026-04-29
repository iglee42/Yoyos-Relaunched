package fr.iglee42.yoyos.data.providers;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.init.YoyosItems;
import fr.iglee42.yoyos.common.YoyosTiers;
import fr.iglee42.yoyos.compat.plugins.TconstructPlugin;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IncrementalModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IncrementalModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.MultilevelModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.function.Consumer;

public class YoyosRecipesGenerator extends RecipeProvider implements IToolRecipeHelper {
    public YoyosRecipesGenerator(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        String partFolder = "tools/parts/";
        String castFolder = "smeltery/casts/";
        partRecipes(consumer, TconstructPlugin.YOYO_PLATE,     TconstructPlugin.YOYO_PLATE_CAST,     4, partFolder, castFolder);
        uncastablePart(consumer,TconstructPlugin.YOYO_CORD.get(),2,null,partFolder);

        MultilevelModifierRecipeBuilder.modifier(TconstructPlugin.ITEM_COLLECTING_MODIFIER)
                .setTools(Ingredient.of(TconstructPlugin.YOYO))
                .addInput(Items.HOPPER,5)
                .addInput(Tags.Items.SLIMEBALLS,10)
                .addLevelRange(SlotType.UPGRADE,1,1,5)
                .saveSalvage(consumer,prefix(TconstructPlugin.ITEM_COLLECTING_MODIFIER,"tools/modifiers/salvage/upgrade/"))
                .save(consumer,prefix(TconstructPlugin.ITEM_COLLECTING_MODIFIER,"tools/modifiers/upgrade/"));

    }

    @Override
    public String getModId() {
        return Yoyos.MODID;
    }
}
