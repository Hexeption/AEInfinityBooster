package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.networking.WirelessTileEntity;
import net.minecraft.tileentity.TileEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.aeinfinitybooster.util.AEItemFilterAlwaysAllow;

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
        inv.setFilter(new AEItemFilterAlwaysAllow());
    }
}
