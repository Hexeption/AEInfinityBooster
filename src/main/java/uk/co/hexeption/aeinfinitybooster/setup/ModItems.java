package uk.co.hexeption.aeinfinitybooster.setup;

import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import uk.co.hexeption.aeinfinitybooster.item.DimensionCard;
import uk.co.hexeption.aeinfinitybooster.item.InfinityCard;

/**
 * ModItems
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 08:50 am
 */
public class ModItems {

    public static final Supplier<Item> INFINITY_CARD = Registration.item("infinity_card", InfinityCard::new);
    public static final Supplier<Item> DIMENSION_CARD = Registration.item("dimension_card", DimensionCard::new);

    static void register() {

    }

}
