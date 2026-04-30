package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.api.inventories.InternalInventory;
import appeng.blockentity.AEBaseBlockEntity;
import appeng.blockentity.networking.WirelessAccessPointBlockEntity;
import appeng.core.definitions.AEItems;
import appeng.util.inv.AppEngInternalInventory;
import appeng.util.inv.filter.IAEItemFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.aeinfinitybooster.config.AEInfinityBoosterConfig;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;
import uk.co.hexeption.aeinfinitybooster.init.IChunkForceable;

@Mixin(value = WirelessAccessPointBlockEntity.class, remap = false)
public abstract class MixinWirelessBlockEntity extends AEBaseBlockEntity implements IChunkForceable {

    @Shadow @Final private AppEngInternalInventory inv;

    public MixinWirelessBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    boolean ib$hasChunkLoadCard() {
        ItemStack card = inv.getStackInSlot(0);
        return card.is(ModItems.DIMENSION_CARD.get()) || card.is(ModItems.INFINITY_CARD.get());
    }

    @Override
    public void ib$setChunkForced(boolean force) {
        if (level instanceof ServerLevel serverLevel) {
            BlockPos pos = getBlockPos();
            serverLevel.setChunkForced(pos.getX() >> 4, pos.getZ() >> 4, force);
        }
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(BlockEntityType<?> type, BlockPos pos, BlockState blockState, CallbackInfo ci) {
        inv.setFilter(new IAEItemFilter() {
            @Override
            public boolean allowExtract(InternalInventory inv, int slot, int amount) {
                return true;
            }

            @Override
            public boolean allowInsert(InternalInventory inv, int slot, ItemStack stack) {
                return AEItems.WIRELESS_BOOSTER.is(stack)
                        || stack.is(ModItems.DIMENSION_CARD.get())
                        || stack.is(ModItems.INFINITY_CARD.get());
            }
        });
    }

    @Inject(method = "onReady", at = @At("RETURN"))
    private void onReady(CallbackInfo ci) {
        if (AEInfinityBoosterConfig.CHUNK_LOADING_ENABLED.get() && ib$hasChunkLoadCard()) {
            ib$setChunkForced(true);
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