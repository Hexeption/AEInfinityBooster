package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.menu.implementations.WirelessMenu;
import appeng.menu.slot.RestrictedInputSlot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

/**
 * MixinWirelessMenu
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 10:35 am
 */
@Mixin(value = WirelessMenu.class, remap = false)
public abstract class MixinWirelessMenu {

    @Shadow
    protected abstract void setRange(long range);

    @Shadow
    @Final
    private RestrictedInputSlot boosterSlot;

    @Inject(method = "broadcastChanges", at = @At(value = "INVOKE", target = "Lappeng/menu/AEBaseMenu;broadcastChanges()V", shift = At.Shift.BEFORE))
    private void broadcastChanges(CallbackInfo ci) {

        if (this.boosterSlot.getItem().is(ModItems.INFINITY_CARD.get()) || this.boosterSlot.getItem().is(ModItems.DIMENSION_CARD.get())) {
            this.setRange(Long.MAX_VALUE);
        }
    }

}
