//? if >=1.17 {
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

//? if <1.20
/*import org.spongepowered.asm.mixin.Shadow;*/
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
    //?} else if >=1.20 {
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
    *///?} else {
    /*@Shadow
    private double sqRange;

    @Shadow
    private double currentDistanceFromGrid;

    public MixinWirelessTerminalMenuHost(Player player, Integer slot, ItemStack itemStack) {
        super(player, slot, itemStack);
    }

    @Inject(method = "testWap", at = @At("HEAD"), cancellable = true)
    private void testWap(IWirelessAccessPoint wirelessAccessPoint, CallbackInfoReturnable<Boolean> cir) {
        Double distance = boostedDistance(wirelessAccessPoint);
        if (distance != null && distance < sqRange) {
            sqRange = distance;
            currentDistanceFromGrid = Math.sqrt(distance);
            cir.setReturnValue(true);
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
        //? if >=1.20 {
        boolean sameDimension = this.getPlayer().level().dimension().equals(wirelessAccessPoint.getLocation().getLevel().dimension());
        //?} else
        /*boolean sameDimension = this.getPlayer().level.dimension().equals(wirelessAccessPoint.getLocation().getLevel().dimension());*/

        if (card.is(ModItems.INFINITY_CARD.get()) && sameDimension) {
            return 256.0D;
        }

        if (card.is(ModItems.DIMENSION_CARD.get())) {
            return 1024.0D;
        }

        return null;
    }
}
//?} else {
/*package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.api.implementations.tiles.IWirelessAccessPoint;
import appeng.helpers.WirelessTerminalGuiObject;
import appeng.tile.networking.WirelessTileEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

@Mixin(value = WirelessTerminalGuiObject.class, remap = false)
public class MixinWirelessTerminalMenuHost {

    @Shadow
    @Final
    private Player myPlayer;

    @Shadow
    private double sqRange;

    @Shadow
    private double myRange;

    @Inject(method = "testWap", at = @At("HEAD"), cancellable = true)
    private void testWap(IWirelessAccessPoint wirelessAccessPoint, CallbackInfoReturnable<Boolean> cir) {
        Double distance = boostedDistance(wirelessAccessPoint);
        if (distance != null && distance < sqRange) {
            sqRange = distance;
            myRange = Math.sqrt(distance);
            cir.setReturnValue(true);
        }
    }

    @Unique
    private Double boostedDistance(IWirelessAccessPoint wirelessAccessPoint) {
        if (!wirelessAccessPoint.isActive() || !(wirelessAccessPoint instanceof WirelessTileEntity)) {
            return null;
        }

        ItemStack card = ((WirelessTileEntity) wirelessAccessPoint).getInternalInventory().getStackInSlot(0);
        boolean sameDimension = myPlayer.level.dimension().equals(wirelessAccessPoint.getLocation().getWorld().dimension());

        if (card.is(ModItems.INFINITY_CARD.get()) && sameDimension) {
            return 256.0D;
        }

        if (card.is(ModItems.DIMENSION_CARD.get())) {
            return 1024.0D;
        }

        return null;
    }
}
*///?}
