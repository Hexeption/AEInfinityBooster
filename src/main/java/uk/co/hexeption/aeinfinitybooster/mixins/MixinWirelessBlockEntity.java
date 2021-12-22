package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.api.inventories.InternalInventory;
import appeng.blockentity.networking.WirelessBlockEntity;
import appeng.core.definitions.AEItems;
import appeng.util.inv.AppEngInternalInventory;
import appeng.util.inv.filter.IAEItemFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

/**
 * MixinWirelessBlockEntity
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 09:06 am
 */
@Mixin(value = WirelessBlockEntity.class, remap = false)
public class MixinWirelessBlockEntity {

    @Shadow @Final private AppEngInternalInventory inv;

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(BlockEntityType blockEntityType, BlockPos pos, BlockState blockState, CallbackInfo ci){
        inv.setFilter(new IAEItemFilter() {
            @Override
            public boolean allowExtract(InternalInventory inv, int slot, int amount) {
                return true;
            }

            @Override
            public boolean allowInsert(InternalInventory inv, int slot, ItemStack stack) {
                return true;
            }
        });
    }
}
