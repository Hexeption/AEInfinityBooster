package uk.co.hexeption.aeinfinitybooster;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = AEInfinityBooster.MODID, dist = Dist.CLIENT)
public class AEInfinityBoosterClient {

    public AEInfinityBoosterClient(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
