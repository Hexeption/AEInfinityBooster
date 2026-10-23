//? if >=1.21.4 {
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
//?} else if forgeLike {
/*package uk.co.hexeption.aeinfinitybooster.datagen;

import uk.co.hexeption.aeinfinitybooster.AEInfinityBooster;

//? if neoforge {
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
//?} else {
/^import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
^///?}
//? if >=1.19.3 {
import net.minecraft.data.PackOutput;
//?} else
/^import net.minecraft.data.DataGenerator;^/

public class ModelGenerator extends ItemModelProvider {
    //? if >=1.19.3 {
    public ModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AEInfinityBooster.MODID, existingFileHelper);
    }
    //?} else {
    /^public ModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AEInfinityBooster.MODID, existingFileHelper);
    }
    ^///?}

    @Override
    protected void registerModels() {
        flatItem("infinity_card");
        flatItem("dimension_card");
    }

    private void flatItem(String name) {
        withExistingParent(name, mcLoc("item/generated")).texture("layer0", modLoc("item/" + name));
    }
}
*///?}
