package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.networking.WirelessTileEntity;
import appeng.util.inv.filter.IAEItemFilter;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MixinWirelessTileEntity
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 09:06 am
 */
@Mixin(value = WirelessTileEntity.class, remap = false)
public class MixinWirelessTileEntity {

    @Shadow
    @Final
    private AppEngInternalInventory inv;

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(TileEntityType tileEntityTypeIn, CallbackInfo ci) {
        inv.setFilter(new IAEItemFilter() {

            @Override
            public boolean allowExtract(IItemHandler inv, int slot, int amount) {
                return true;
            }

            @Override
            public boolean allowInsert(IItemHandler inv, int slot, ItemStack stack) {
                return true;
            }
        });
    }
}
