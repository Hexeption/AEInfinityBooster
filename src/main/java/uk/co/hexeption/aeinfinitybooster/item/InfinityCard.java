package uk.co.hexeption.aeinfinitybooster.item;

import net.minecraft.world.item.Item;
import uk.co.hexeption.aeinfinitybooster.AEInfinityBooster;

/**
 * InfinityCard
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 08:51 am
 */
public class InfinityCard extends Item {
    public InfinityCard() {
        super(new Item.Properties().tab(AEInfinityBooster.CREATIVE_MODE_TAB).durability(0));
    }



}
