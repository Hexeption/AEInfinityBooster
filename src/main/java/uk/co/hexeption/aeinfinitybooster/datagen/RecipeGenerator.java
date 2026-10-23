//? if >=1.20 {
package uk.co.hexeption.aeinfinitybooster.datagen;

import appeng.core.definitions.AEItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

//? if >=1.20.5 && <26.3 {
/*import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
*///?}
//? if >=1.20.2 && <26.3 {
/*import net.minecraft.data.recipes.RecipeOutput;
*///?} else if <1.20.2 {
/*import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
*///?}
//? if >=1.21.2 && <26.3
/*import uk.co.hexeption.aeinfinitybooster.AEInfinityBooster;*/
//? if <26.3
/*import net.minecraft.data.PackOutput;*/
//? if >=26.3 {
import net.minecraft.advancements.Advancement;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;
//?}

/**
 * RecipeGenerator
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 14/02/2021 - 06:35 pm
 */
public class RecipeGenerator extends RecipeProvider {

    //? if >=26.3 {
    public RecipeGenerator(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        super(recipes, advancements);
    }

    @Override
    protected void buildRecipes() {
    //?} else if >=1.21.2 {
    /*protected RecipeGenerator(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
    *///?} else if >=1.20.5 {
    /*public RecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
    *///?} else if >=1.20.2 {
    /*public RecipeGenerator(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
    *///?} else {
    /*public RecipeGenerator(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> output) {
    *///?}
        //? if >=1.21.2 {
        shaped(RecipeCategory.MISC, ModItems.INFINITY_CARD.get())
        //?} else
        /*ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INFINITY_CARD.get())*/
                .pattern("ERE").pattern("RSR").pattern("NNN")
                .define('E', Items.ENDER_EYE)
                .define('R', AEItems.WIRELESS_BOOSTER.asItem())
                .define('S', Items.NETHER_STAR)
                .define('N', Items.NETHERITE_INGOT)
                .unlockedBy("has_item", has(Items.NETHERITE_INGOT))
                .save(output);
        //? if >=1.21.2 {
        shaped(RecipeCategory.MISC, ModItems.DIMENSION_CARD.get())
        //?} else
        /*ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIMENSION_CARD.get())*/
                .pattern("RNR").pattern("NEN").pattern("RNR")
                .define('R', ModItems.INFINITY_CARD.get())
                .define('E', Items.ENDER_EYE)
                .define('N', Items.NETHER_STAR)
                .unlockedBy("has_item", has(ModItems.INFINITY_CARD.get()))
                .save(output);
    }

    //? if >=1.21.2 && <26.3 {
    /*public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new RecipeGenerator(provider, output);
        }

        @Override
        public String getName() {
            return AEInfinityBooster.MODID + " recipe generator";
        }
    }
    *///?}
}
//?}
