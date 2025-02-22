package uk.co.hexeption.aeinfinitybooster;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.hexeption.aeinfinitybooster.config.AEInfinityBoosterConfig;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;
import uk.co.hexeption.aeinfinitybooster.setup.Registration;

import java.lang.reflect.Field;

@Mod("aeinfinitybooster")
public class AEInfinityBooster {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "aeinfinitybooster";

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AEBOOSTER_TAB = CREATIVE_MODE_TAB.register("aebooster", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + MODID + ".tab"))
            .icon(() -> new ItemStack(ModItems.INFINITY_CARD.get()))
            .displayItems(((pParameters, pOutput) -> {
                for (Field field : ModItems.class.getFields()) {
                    if(field.getType() != DeferredItem.class) continue;

                    try{
                        DeferredItem<Item> item = (DeferredItem<Item>) field.get(null);
                        pOutput.accept(new ItemStack(item.get()));
                    }catch (IllegalAccessException e){

                    }
                }
            }))
            .build());

    public AEInfinityBooster(IEventBus modEventBus, ModContainer modContainer) {

        Registration.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, AEInfinityBoosterConfig.CONFIG_SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);


        CREATIVE_MODE_TAB.register(modEventBus);

    }

}
