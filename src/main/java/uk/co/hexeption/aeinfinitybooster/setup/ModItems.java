package uk.co.hexeption.aeinfinitybooster.setup;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import uk.co.hexeption.aeinfinitybooster.item.InfinityCard;

/**
 * ModItems
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 08:50 am
 */
public class ModItems {

    public static final RegistryObject<Item> INFINITY_CARD = Registration.ITEMS.register("infinity_card", InfinityCard::new);

    static void register() {

    }

}
