package uk.co.hexeption.aeinfinitybooster.mixins;

//? if >=1.17 {
import appeng.menu.implementations.WirelessAccessPointMenu;
import appeng.menu.slot.RestrictedInputSlot;
//?} else {
/*import appeng.container.implementations.WirelessContainer;
import appeng.container.slot.RestrictedInputSlot;
*///?}
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.aeinfinitybooster.config.AEInfinityBoosterConfig;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

/**
 * MixinWirelessMenu
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 10:35 am
 */
//? if >=1.17 {
@Mixin(WirelessAccessPointMenu.class)
//?} else
/*@Mixin(WirelessContainer.class)*/
public abstract class MixinWirelessMenu {

    @Shadow(remap = false)
    protected abstract void setRange(long range);

    @Shadow(remap = false)
    @Final
    private RestrictedInputSlot boosterSlot;

    @Shadow(remap = false)
    protected abstract void setDrain(long drain);

    //? if >=1.17 {
    @Inject(method = "broadcastChanges", at = @At(value = "INVOKE", target = "Lappeng/menu/AEBaseMenu;broadcastChanges()V", shift = At.Shift.BEFORE))
    //?} else
    /*@Inject(method = "broadcastChanges", at = @At(value = "INVOKE", target = "Lappeng/container/AEBaseContainer;broadcastChanges()V", shift = At.Shift.BEFORE))*/
    private void broadcastChanges(CallbackInfo ci) {

        if (this.boosterSlot.getItem().is(ModItems.INFINITY_CARD.get()) || this.boosterSlot.getItem().is(ModItems.DIMENSION_CARD.get())) {
            this.setRange(Long.MAX_VALUE);
        }

        if (this.boosterSlot.getItem().is(ModItems.INFINITY_CARD.get())) {
            this.setDrain((long) 100 * AEInfinityBoosterConfig.INFINITY_CARD_DRAIN.get());
        }

        if (this.boosterSlot.getItem().is(ModItems.DIMENSION_CARD.get())) {
            this.setDrain((long) 100 * AEInfinityBoosterConfig.DIMENSION_CARD_DRAIN.get());
        }
    }

}
