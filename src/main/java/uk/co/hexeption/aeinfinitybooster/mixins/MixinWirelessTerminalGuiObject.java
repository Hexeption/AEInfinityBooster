package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.api.implementations.tiles.IWirelessAccessPoint;
import appeng.api.networking.IGridHost;
import appeng.helpers.WirelessTerminalGuiObject;
import appeng.tile.networking.WirelessTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

/**
 * MixinWirelessTerminalMenuHost
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 08:27 am
 */
@Mixin(value = WirelessTerminalGuiObject.class, remap = false)
public class MixinWirelessTerminalGuiObject {

    @Shadow
    private double myRange;

    @Shadow
    @Final
    private PlayerEntity myPlayer;

    @Inject(method = "testWap", at = @At("HEAD"), cancellable = true)
    private void testWap(IWirelessAccessPoint wirelessAccessPoint, CallbackInfoReturnable<Boolean> cir) {

        wirelessAccessPoint.getGrid().getMachines(WirelessTileEntity.class).forEach(iGridNode -> {
            WirelessTileEntity wirelessBlockEntity = (WirelessTileEntity) iGridNode.getMachine();

            if (wirelessBlockEntity.getInternalInventory().getStackInSlot(0).getItem() == ModItems.DIMENSION_CARD.get()) {
                myRange = 32;
                cir.setReturnValue(true);
            }

            if (!this.myPlayer.level.dimension().location().toString().equals(wirelessAccessPoint.getLocation().getWorld().dimension().location().toString())) {
                return;
            }

            if (wirelessBlockEntity.getInternalInventory().getStackInSlot(0).getItem() == ModItems.INFINITY_CARD.get()) {
                myRange = 16;
                cir.setReturnValue(true);
            }
        });
    }

}
