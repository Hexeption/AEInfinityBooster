package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.menu.slot.RestrictedInputSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

/**
 * MixinRestrictedInputSlot
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 10:10 am
 */
@Mixin(value = RestrictedInputSlot.class, remap = false)
public class MixinRestrictedInputSlot {

    @Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
    private void mayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.is(ModItems.INFINITY_CARD.get())) {
            cir.setReturnValue(true);
        }
    }

}
