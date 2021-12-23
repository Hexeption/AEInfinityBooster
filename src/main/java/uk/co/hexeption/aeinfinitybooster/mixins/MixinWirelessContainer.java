package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.container.implementations.WirelessContainer;
import appeng.container.slot.RestrictedInputSlot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

/**
 * MixinWirelessContainer
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 10:35 am
 */
@Mixin(value = WirelessContainer.class)
public abstract class MixinWirelessContainer {

    @Shadow
    protected abstract void setRange(long range);

    @Shadow
    @Final
    private RestrictedInputSlot boosterSlot;

    @Inject(method = "broadcastChanges",  at = @At(value = "INVOKE", target = "Lappeng/container/AEBaseContainer;broadcastChanges()V", shift = At.Shift.BEFORE))
    private void detectAndSendChanges(CallbackInfo ci) {

        if (this.boosterSlot.getItem().getItem() == ModItems.INFINITY_CARD.get() || this.boosterSlot.getItem().getItem() == ModItems.DIMENSION_CARD.get()) {
            this.setRange(Long.MAX_VALUE);
        }
    }

}
