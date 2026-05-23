package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.api.implementations.blockentities.IWirelessAccessPoint;
import appeng.api.implementations.menuobjects.ItemMenuHost;
import appeng.blockentity.networking.WirelessAccessPointBlockEntity;
import appeng.helpers.WirelessTerminalMenuHost;
import appeng.menu.locator.ItemMenuHostLocator;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
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

    public MixinWirelessTerminalMenuHost(Item item, Player player, ItemMenuHostLocator locator) {

        super(item, player, locator);
    }

    @Inject(method = "getAccessPointSignal", at = @At("HEAD"), cancellable = true)
    private void testWap(IWirelessAccessPoint wirelessAccessPoint, CallbackInfoReturnable<WirelessTerminalMenuHost.AccessPointSignal> cir) {

        wirelessAccessPoint.getGrid().getMachines(WirelessAccessPointBlockEntity.class).forEach(wirelessBlockEntity -> {

            if (wirelessBlockEntity.getInternalInventory().getStackInSlot(0).is(ModItems.DIMENSION_CARD.get())) {
                cir.setReturnValue(new WirelessTerminalMenuHost.AccessPointSignal(1024.0D, 1024.0D));
            }

            if (!this.getPlayer().level().dimension().identifier().toString().equals(wirelessAccessPoint.getLocation().getLevel().dimension().identifier().toString())) {
                return;
            }

            if (wirelessBlockEntity.getInternalInventory().getStackInSlot(0).is(ModItems.INFINITY_CARD.get())) {
                cir.setReturnValue(new WirelessTerminalMenuHost.AccessPointSignal(256.0D, 256.0D));
            }
        });
    }
}
