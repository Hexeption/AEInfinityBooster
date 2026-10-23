//? if forgeLike {
package uk.co.hexeption.aeinfinitybooster.datagen;

import net.minecraft.data.DataGenerator;
import uk.co.hexeption.aeinfinitybooster.AEInfinityBooster;

//? if neoforge {
//? if >=26.3
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
//? if >=1.20.5 {
import net.neoforged.fml.common.EventBusSubscriber;
//?} else
/*import net.neoforged.fml.common.Mod;*/
//?} else {
/*import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
*///?}
//? if <1.21.4
/*import net.minecraft.data.DataProvider;*/

/**
 * RSInfinityBoosterDataGen
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 14/02/2021 - 06:48 pm
 */
//? if >=1.21.4 {
@EventBusSubscriber(modid = AEInfinityBooster.MODID)
//?} else if >=1.20.5 {
/*@EventBusSubscriber(modid = AEInfinityBooster.MODID, bus = EventBusSubscriber.Bus.MOD)
*///?} else
/*@Mod.EventBusSubscriber(modid = AEInfinityBooster.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)*/
public class DataGenerators {

    private DataGenerators() {
    }

    //? if >=26.3 {
    // Recipes are registry entries from 26.3, so they are gathered separately from the model provider
    @SubscribeEvent
    public static void onGatherRegistries(GatherDataRegistryEntriesEvent event) {
        event.recipe(RecipeGenerator::new);
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();

        event.addProvider(new ModelGenerator(generator.getPackOutput()));
    }
    //?} else if >=1.21.4 {
    /*@SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();

        event.addProvider(new RecipeGenerator.Runner(generator.getPackOutput(), event.getLookupProvider()));
        event.addProvider(new ModelGenerator(generator.getPackOutput()));
    }
    *///?} else if >=1.20.5 {
    /*@SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        generator.addProvider(event.includeServer(), (DataProvider.Factory<RecipeGenerator>) output -> new RecipeGenerator(output, event.getLookupProvider()));
    }
    *///?} else {
    /*@SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        generator.addProvider(event.includeServer(), (DataProvider.Factory<RecipeGenerator>) RecipeGenerator::new);
    }
    *///?}
}
//?}
