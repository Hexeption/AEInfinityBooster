package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.api.implementations.blockentities.IWirelessAccessPoint;
import appeng.api.implementations.menuobjects.ItemMenuHost;
import appeng.blockentity.networking.WirelessAccessPointBlockEntity;
import appeng.helpers.WirelessTerminalMenuHost;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
@Mixin(value = WirelessTerminalMenuHost.class, remap = false)
public class MixinWirelessTerminalMenuHost extends ItemMenuHost {

    @Shadow
    private double currentDistanceFromGrid;

    public MixinWirelessTerminalMenuHost(Player player, int slot, ItemStack itemStack) {
        super(player, slot, itemStack);
    }

    @Inject(method = "bestWap", at = @At("HEAD"), cancellable = true)
    private void bestWap(IWirelessAccessPoint wirelessAccessPoint, CallbackInfoReturnable<Boolean> cir) {

        wirelessAccessPoint.getGrid().getMachines(WirelessAccessPointBlockEntity.class).forEach(wirelessBlockEntity -> {

            if (wirelessBlockEntity.getInternalInventory().getStackInSlot(0).is(ModItems.DIMENSION_CARD.get())) {
                currentDistanceFromGrid = 32;
                cir.setReturnValue(true);
            }

            if (!this.getPlayer().level().dimension().location().toString().equals(wirelessAccessPoint.getLocation().getLevel().dimension().location().toString())) {
                return;
            }

            if (wirelessBlockEntity.getInternalInventory().getStackInSlot(0).is(ModItems.INFINITY_CARD.get())) {
                currentDistanceFromGrid = 16;
                cir.setReturnValue(true);
            }
        });
    }

}
