package uk.co.hexeption.aeinfinitybooster;

import uk.co.hexeption.aeinfinitybooster.config.AEInfinityBoosterConfig;
import uk.co.hexeption.aeinfinitybooster.setup.Registration;

//? if neoforge {
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
//? if <1.20.5
/*import net.neoforged.fml.ModLoadingContext;*/
//?}
//? if forge {
/*import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
*///?}
//? if fabric {
/*import net.fabricmc.api.ModInitializer;
*///?}

//? if forgeLike {
@Mod(AEInfinityBooster.MODID)
public class AEInfinityBooster {
//?} else {
/*public class AEInfinityBooster implements ModInitializer {
*///?}

    public static final String MODID = "aeinfinitybooster";

    //? if neoforge {
    public AEInfinityBooster(IEventBus modEventBus, ModContainer modContainer) {

        Registration.register(modEventBus);

        //? if >=26.3 {
        modContainer.registerConfig(ModConfig.Type.LOCAL, AEInfinityBoosterConfig.CONFIG_SPEC);
        //?} else if >=1.20.5 {
        /*modContainer.registerConfig(ModConfig.Type.COMMON, AEInfinityBoosterConfig.CONFIG_SPEC);
        *///?} else {
        /*ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AEInfinityBoosterConfig.CONFIG_SPEC);
        *///?}

    }
    //?}
    //? if forge {
    /*public AEInfinityBooster() {

        Registration.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AEInfinityBoosterConfig.CONFIG_SPEC);

    }
    *///?}
    //? if fabric {
    /*@Override
    public void onInitialize() {

        AEInfinityBoosterConfig.register();

        Registration.register();

    }
    *///?}

}
