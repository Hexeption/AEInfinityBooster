package uk.co.hexeption.aeinfinitybooster.util;

import appeng.api.inventories.InternalInventory;
import appeng.core.definitions.AEItems;
import appeng.util.inv.filter.IAEItemFilter;
import net.minecraft.world.item.ItemStack;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

public class AccessPointCardFilter implements IAEItemFilter {

    @Override
    public boolean allowExtract(InternalInventory inv, int slot, int amount) {
        return true;
    }

    @Override
    public boolean allowInsert(InternalInventory inv, int slot, ItemStack stack) {
        return stack.is(AEItems.WIRELESS_BOOSTER.asItem()) || stack.is(ModItems.DIMENSION_CARD.get()) || stack.is(ModItems.INFINITY_CARD.get());
    }
}
