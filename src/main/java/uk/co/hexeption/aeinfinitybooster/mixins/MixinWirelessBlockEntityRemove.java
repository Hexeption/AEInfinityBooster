package uk.co.hexeption.aeinfinitybooster.mixins;

import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.aeinfinitybooster.init.IChunkForceable;

@Mixin(BlockEntity.class)
public abstract class MixinWirelessBlockEntityRemove {

    @Inject(method = "setRemoved", at = @At("HEAD"))
    private void onSetRemoved(CallbackInfo ci) {
        if ((Object) this instanceof IChunkForceable) {
            ((IChunkForceable) (Object) this).ib$setChunkForced(false);
        }
    }
}
