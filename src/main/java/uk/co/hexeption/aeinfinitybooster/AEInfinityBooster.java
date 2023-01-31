package uk.co.hexeption.aeinfinitybooster;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;
import uk.co.hexeption.aeinfinitybooster.setup.Registration;

import java.lang.reflect.Field;

@Mod("aeinfinitybooster")
public class AEInfinityBooster {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String ID = "aeinfinitybooster";

    public static CreativeModeTab CREATIVE_MODE_TAB;

    public AEInfinityBooster() {

        Registration.register();

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::createCreativeModeTab);

    }

    @SubscribeEvent
    public void createCreativeModeTab(CreativeModeTabEvent.Register event) {
        LOGGER.info("Creating Creative Mode Tab");
        CREATIVE_MODE_TAB = event.registerCreativeModeTab(new ResourceLocation(ID, "aeinfinitybooster"), builder -> builder
                .title(Component.translatable("item_group." + ID + ".tab"))
                .icon(() -> new ItemStack(ModItems.INFINITY_CARD.get()))
                .displayItems((enabledFlags, populator, hasPermissions) -> {

                    for (Field field : ModItems.class.getFields()) {
                        if (field.getType() != RegistryObject.class) continue;

                        try {
                            RegistryObject<Item> item = (RegistryObject<Item>) field.get(null);
                            populator.accept(new ItemStack(item.get()));
                        } catch (IllegalAccessException e) {
                        }
                    }

                })
        );
    }

}
