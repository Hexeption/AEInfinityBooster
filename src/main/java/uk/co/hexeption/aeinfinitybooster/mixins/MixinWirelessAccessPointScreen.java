package uk.co.hexeption.aeinfinitybooster.mixins;

import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.implementations.WirelessAccessPointScreen;
import appeng.client.gui.style.ScreenStyle;
import appeng.core.localization.GuiText;
import appeng.menu.implementations.WirelessAccessPointMenu;
import appeng.util.Platform;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if <1.19
/*import net.minecraft.network.chat.TextComponent;*/

@Mixin(value = WirelessAccessPointScreen.class, remap = false)
public class MixinWirelessAccessPointScreen extends AEBaseScreen<WirelessAccessPointMenu> {

    public MixinWirelessAccessPointScreen(WirelessAccessPointMenu menu, Inventory playerInventory, Component title, ScreenStyle style) {

        super(menu, playerInventory, title, style);
    }

    @Inject(method = "updateBeforeRender", at = @At("HEAD"), cancellable = true)
    private void updateBeforeRender(CallbackInfo ci) {
        super.updateBeforeRender();

        //? if >=1.19 {
        Component rangeText = Component.empty();
        Component energyUseText = Component.empty();
        //?} else {
        /*Component rangeText = TextComponent.EMPTY;
        Component energyUseText = TextComponent.EMPTY;
        *///?}
        if (menu.getRange() > 0) {
            double rangeBlocks = menu.getRange() / 10.0;
            rangeText = GuiText.WirelessRange.text(rangeBlocks);
            energyUseText = GuiText.PowerUsageRate.text(Platform.formatPowerLong(menu.getDrain(), true));
        }

        if(menu.getRange() == Long.MAX_VALUE) {
            rangeText = GuiText.WirelessRange.text("∞");
            energyUseText = GuiText.PowerUsageRate.text(Platform.formatPowerLong(menu.getDrain(), true));
        }

        setTextContent("range", rangeText);
        setTextContent("energy_use", energyUseText);

        ci.cancel();
    }
}
