package uk.co.hexeption.aeinfinitybooster.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import uk.co.hexeption.aeinfinitybooster.AEInfinityBooster;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

public class ModelGenerator extends ModelProvider {
    public ModelGenerator(PackOutput output) {
        super(output, AEInfinityBooster.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.INFINITY_CARD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DIMENSION_CARD.get(), ModelTemplates.FLAT_ITEM);
    }


}
