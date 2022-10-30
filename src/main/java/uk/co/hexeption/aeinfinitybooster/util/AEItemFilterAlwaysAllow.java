package uk.co.hexeption.aeinfinitybooster.util;

import appeng.util.inv.filter.IAEItemFilter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class AEItemFilterAlwaysAllow implements IAEItemFilter {

    @Override
    public boolean allowExtract(IItemHandler inv, int slot, int amount) {
        return true;
    }

    @Override
    public boolean allowInsert(IItemHandler inv, int slot, ItemStack stack) {
        return true;
    }
}
