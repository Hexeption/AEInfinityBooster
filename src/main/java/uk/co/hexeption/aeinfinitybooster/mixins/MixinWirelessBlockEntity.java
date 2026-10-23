package uk.co.hexeption.aeinfinitybooster.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.aeinfinitybooster.config.AEInfinityBoosterConfig;
import uk.co.hexeption.aeinfinitybooster.init.IChunkForceable;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;
import uk.co.hexeption.aeinfinitybooster.util.AccessPointCardFilter;

//? if >=1.17 {
import appeng.blockentity.AEBaseBlockEntity;
import appeng.blockentity.networking.WirelessAccessPointBlockEntity;
import appeng.util.inv.AppEngInternalInventory;
//?} else {
/*import appeng.tile.AEBaseTileEntity;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.networking.WirelessTileEntity;
*///?}

/**
 * MixinWirelessBlockEntity
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 09:06 am
 */
//? if >=1.17 {
@Mixin(value = WirelessAccessPointBlockEntity.class, remap = false)
public abstract class MixinWirelessBlockEntity extends AEBaseBlockEntity implements IChunkForceable {
//?} else {
/*@Mixin(value = WirelessTileEntity.class, remap = false)
public abstract class MixinWirelessBlockEntity extends AEBaseTileEntity implements IChunkForceable {
*///?}

    @Shadow @Final private AppEngInternalInventory inv;

    //? if >=1.17 {
    public MixinWirelessBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
    //?} else {
    /*public MixinWirelessBlockEntity(BlockEntityType<?> type) {
        super(type);
    }
    *///?}

    boolean ib$hasChunkLoadCard() {
        ItemStack card = inv.getStackInSlot(0);
        return card.is(ModItems.DIMENSION_CARD.get()) || card.is(ModItems.INFINITY_CARD.get());
    }

    // Whether this access point is what keeps its chunk forced. It never releases a chunk that something else forced,
    // like /forceload or another access point in the same chunk.
    @Unique
    private boolean ib$forcedChunk;

    @Override
    public void ib$setChunkForced(boolean force) {
        if (force == ib$forcedChunk || !(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel) level;

        BlockPos pos = getBlockPos();
        boolean changed = serverLevel.setChunkForced(pos.getX() >> 4, pos.getZ() >> 4, force);
        ib$forcedChunk = force && changed;
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    //? if >=1.17 {
    private void init(BlockEntityType blockEntityType, BlockPos pos, BlockState blockState, CallbackInfo ci){
    //?} else
    /*private void init(BlockEntityType blockEntityType, CallbackInfo ci){*/
        inv.setFilter(new AccessPointCardFilter());
    }

    @Inject(method = "onReady", at = @At("RETURN"))
    private void onReady(CallbackInfo ci) {
        if (AEInfinityBoosterConfig.CHUNK_LOADING_ENABLED.get() && ib$hasChunkLoadCard()) {
            ib$setChunkForced(true);
            // The chunk is usually still forced from before the world was saved, and that was this access point
            ib$forcedChunk = true;
        }
    }

    @Inject(method = "saveChanges", at = @At("HEAD"))
    private void onSaveChanges(CallbackInfo ci) {
        if (AEInfinityBoosterConfig.CHUNK_LOADING_ENABLED.get()) {
            ib$setChunkForced(ib$hasChunkLoadCard());
        } else {
            ib$setChunkForced(false);
        }
    }
}
