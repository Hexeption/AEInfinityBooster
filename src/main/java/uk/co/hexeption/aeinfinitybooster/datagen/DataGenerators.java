package uk.co.hexeption.aeinfinitybooster.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import uk.co.hexeption.aeinfinitybooster.AEInfinityBooster;

/**
 * RSInfinityBoosterDataGen
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 14/02/2021 - 06:48 pm
 */
@EventBusSubscriber(modid = AEInfinityBooster.MODID)
public class DataGenerators {

    private DataGenerators() {
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();

        event.addProvider(new RecipeGenerator.Runner(generator.getPackOutput(), event.getLookupProvider()));
        event.addProvider(new ModelGenerator(generator.getPackOutput()));
    }
}
