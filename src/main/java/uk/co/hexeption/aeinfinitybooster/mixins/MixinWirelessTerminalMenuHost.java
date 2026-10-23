package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.api.implementations.blockentities.IWirelessAccessPoint;
import appeng.api.implementations.menuobjects.ItemMenuHost;
import appeng.blockentity.networking.WirelessAccessPointBlockEntity;
import appeng.helpers.WirelessTerminalMenuHost;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

//? if >=1.20.4 {
import appeng.menu.locator.ItemMenuHostLocator;
import net.minecraft.world.item.Item;
//?}

/**
 * MixinWirelessTerminalMenuHost
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 08:27 am
 */
@Mixin(value = WirelessTerminalMenuHost.class, remap = false)
public class MixinWirelessTerminalMenuHost extends ItemMenuHost {

    //? if >=1.20.4 {
    public MixinWirelessTerminalMenuHost(Item item, Player player, ItemMenuHostLocator locator) {

        super(item, player, locator);
    }

    @Inject(method = "getAccessPointSignal", at = @At("HEAD"), cancellable = true)
    private void testWap(IWirelessAccessPoint wirelessAccessPoint, CallbackInfoReturnable<WirelessTerminalMenuHost.AccessPointSignal> cir) {
        Double distance = boostedDistance(wirelessAccessPoint);
        if (distance != null) {
            cir.setReturnValue(new WirelessTerminalMenuHost.AccessPointSignal(distance, distance));
        }
    }
    //?} else {
    /*public MixinWirelessTerminalMenuHost(Player player, int slot, ItemStack itemStack) {
        super(player, slot, itemStack);
    }

    @Inject(method = "getWapSqDistance", at = @At("HEAD"), cancellable = true)
    private void testWap(IWirelessAccessPoint wirelessAccessPoint, CallbackInfoReturnable<Double> cir) {
        Double distance = boostedDistance(wirelessAccessPoint);
        if (distance != null) {
            cir.setReturnValue(distance);
        }
    }
    *///?}

    /**
     * AE2 asks about every access point on the grid and connects through the closest, so each one only answers for
     * its own card. One without power or a channel gets no boost, like AE2's own range check.
     *
     * @return the distance to report when this access point holds one of our cards, or null to let AE2 decide
     */
    @Unique
    private Double boostedDistance(IWirelessAccessPoint wirelessAccessPoint) {
        if (!wirelessAccessPoint.isActive() || !(wirelessAccessPoint instanceof WirelessAccessPointBlockEntity wirelessBlockEntity)) {
            return null;
        }

        ItemStack card = wirelessBlockEntity.getInternalInventory().getStackInSlot(0);
        boolean sameDimension = this.getPlayer().level().dimension().equals(wirelessAccessPoint.getLocation().getLevel().dimension());

        if (card.is(ModItems.INFINITY_CARD.get()) && sameDimension) {
            return 256.0D;
        }

        if (card.is(ModItems.DIMENSION_CARD.get())) {
            return 1024.0D;
        }

        return null;
    }
}
