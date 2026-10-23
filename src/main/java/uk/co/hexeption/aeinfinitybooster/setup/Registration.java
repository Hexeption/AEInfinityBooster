package uk.co.hexeption.aeinfinitybooster.setup;

import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import uk.co.hexeption.aeinfinitybooster.AEInfinityBooster;

//? if >=1.19.3 {
import net.minecraft.core.registries.Registries;
//?} else {
/*import net.minecraft.world.item.ItemStack;
*///?}
//? if <1.19
/*import net.minecraft.network.chat.TranslatableComponent;*/

//? if neoforge {
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
//?}
//? if forge {
/*import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
*///?}
//? if fabric {
/*import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
*///?}

/**
 * Registration
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 08:50 am
 */
public class Registration {

    //? if neoforge {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AEInfinityBooster.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AEInfinityBooster.MODID);
    //?}
    //? if forge {
    /*public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AEInfinityBooster.MODID);
    *///?}
    //? if forge && >=1.19.3 {
    /*public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AEInfinityBooster.MODID);
    *///?}

    //? if <1.19.3 {
    /*public static final CreativeModeTab TAB = new CreativeModeTab(AEInfinityBooster.MODID + ".tab") {
        @Override
        public ItemStack makeIcon() {
            return ModItems.INFINITY_CARD.get().getDefaultInstance();
        }

        @Override
        public Component getDisplayName() {
            //? if >=1.19 {
            return Component.translatable("item_group." + AEInfinityBooster.MODID + ".tab");
            //?} else
            /^return new TranslatableComponent("item_group." + AEInfinityBooster.MODID + ".tab");^/
        }
    };
    *///?}

    static Supplier<Item> item(String name, Function<Item.Properties, Item> factory) {
        //? if neoforge {
        return ITEMS.registerItem(name, factory);
        //?}
        //? if forge && >=1.19.3 {
        /*return ITEMS.register(name, () -> factory.apply(new Item.Properties()));
        *///?} else if forge {
        /*return ITEMS.register(name, () -> factory.apply(new Item.Properties().tab(TAB)));
        *///?}
        //? if fabric {
        /*Item item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AEInfinityBooster.MODID, name), factory.apply(new Item.Properties()));
        return () -> item;
        *///?}
    }

    //? if >=1.19.3 {
    private static CreativeModeTab.Builder tab() {
        //? if fabric {
        /*return FabricItemGroup.builder()
        *///?} else {
        return CreativeModeTab.builder()
        //?}
                .title(Component.translatable("item_group." + AEInfinityBooster.MODID + ".tab"))
                .icon(() -> ModItems.INFINITY_CARD.get().getDefaultInstance())
                .displayItems((parameters, output) -> {
                    output.accept(ModItems.INFINITY_CARD.get());
                    output.accept(ModItems.DIMENSION_CARD.get());
                });
    }
    //?}

    //? if forgeLike && >=1.19.3 {
    public static void register(IEventBus eventBus) {
        ModItems.register();
        CREATIVE_MODE_TABS.register("aebooster", () -> tab().build());

        ITEMS.register(eventBus);
        CREATIVE_MODE_TABS.register(eventBus);
    }
    //?} else if forgeLike {
    /*public static void register(IEventBus eventBus) {
        ModItems.register();

        ITEMS.register(eventBus);
    }
    *///?} else {
    /*public static void register() {
        ModItems.register();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(AEInfinityBooster.MODID, "aebooster"), tab().build());
    }
    *///?}

}
