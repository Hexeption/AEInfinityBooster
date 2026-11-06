package uk.co.hexeption.aeinfinitybooster.setup;

//? if neoforge {
import net.neoforged.neoforge.registries.DeferredItem;
//?} else if forge {
/*import net.minecraftforge.registries.RegistryObject;
*///?} else {
/*import java.util.function.Supplier;
*///?}
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

    // Keep the loader-specific field descriptors: addons compiled against older releases link to these types.
    //? if neoforge {
    public static final DeferredItem<Item> INFINITY_CARD = Registration.item("infinity_card", InfinityCard::new);
    public static final DeferredItem<Item> DIMENSION_CARD = Registration.item("dimension_card", DimensionCard::new);
    //?} else if forge {
    /*public static final RegistryObject<Item> INFINITY_CARD = Registration.item("infinity_card", InfinityCard::new);
    public static final RegistryObject<Item> DIMENSION_CARD = Registration.item("dimension_card", DimensionCard::new);
    *///?} else {
    /*public static final Supplier<Item> INFINITY_CARD = Registration.item("infinity_card", InfinityCard::new);
    public static final Supplier<Item> DIMENSION_CARD = Registration.item("dimension_card", DimensionCard::new);
    *///?}

    static void register() {

    }

}
