package uk.co.hexeption.aeinfinitybooster.setup;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import uk.co.hexeption.aeinfinitybooster.item.DimensionCard;
import uk.co.hexeption.aeinfinitybooster.item.InfinityCard;

/**
 * ModItems
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 08:50 am
 */
public class ModItems {

    public static final DeferredItem<Item> INFINITY_CARD = Registration.ITEMS.registerItem("infinity_card", InfinityCard::new);
    public static final DeferredItem<Item> DIMENSION_CARD = Registration.ITEMS.registerItem("dimension_card", DimensionCard::new);

    static void register() {

    }

}
