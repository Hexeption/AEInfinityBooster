package uk.co.hexeption.aeinfinitybooster.config;

//? if forgeLike {
import org.apache.commons.lang3.tuple.Pair;
//?}
//? if neoforge {
import net.neoforged.neoforge.common.ModConfigSpec;
//?}
//? if forge {
/*import net.minecraftforge.common.ForgeConfigSpec;
*///?}
//? if fabric {
/*import java.util.function.Supplier;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
*///?}


//? if fabric {
/*@Config(name = "aeinfinitybooster")
public class AEInfinityBoosterConfig implements ConfigData {
*///?} else
public class AEInfinityBoosterConfig {

    //? if neoforge {
    public static final AEInfinityBoosterConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public static ModConfigSpec.IntValue DIMENSION_CARD_DRAIN;
    public static ModConfigSpec.IntValue INFINITY_CARD_DRAIN;
    public static ModConfigSpec.BooleanValue CHUNK_LOADING_ENABLED;

    private AEInfinityBoosterConfig(ModConfigSpec.Builder builder) {
        builder.comment("General settings for AEInfinityBooster").push("general");
        DIMENSION_CARD_DRAIN = builder.comment("The amount of power the Dimension Card drains per tick")
                .defineInRange("dimensionCardDrain", 100, 0, Integer.MAX_VALUE);
        INFINITY_CARD_DRAIN = builder.comment("The amount of power the Infinity Card drains per tick")
                .defineInRange("infinityCardDrain", 50, 0, Integer.MAX_VALUE);
        CHUNK_LOADING_ENABLED = builder.comment(
                        "If true, inserting a Dimension Card or Infinity Card into a Wireless Access Point",
                        "will keep the chunk loaded even when no players are nearby.")
                .define("chunkLoadingEnabled", true);
        builder.pop();
    }

    static {
        Pair<AEInfinityBoosterConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(AEInfinityBoosterConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
    //?}
    //? if forge {
    /*public static final AEInfinityBoosterConfig CONFIG;
    public static final ForgeConfigSpec CONFIG_SPEC;

    public static ForgeConfigSpec.IntValue DIMENSION_CARD_DRAIN;
    public static ForgeConfigSpec.IntValue INFINITY_CARD_DRAIN;
    public static ForgeConfigSpec.BooleanValue CHUNK_LOADING_ENABLED;

    private AEInfinityBoosterConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("General settings for AEInfinityBooster").push("general");
        DIMENSION_CARD_DRAIN = builder.comment("The amount of power the Dimension Card drains per tick")
                .defineInRange("dimensionCardDrain", 100, 0, Integer.MAX_VALUE);
        INFINITY_CARD_DRAIN = builder.comment("The amount of power the Infinity Card drains per tick")
                .defineInRange("infinityCardDrain", 50, 0, Integer.MAX_VALUE);
        CHUNK_LOADING_ENABLED = builder.comment(
                        "If true, inserting a Dimension Card or Infinity Card into a Wireless Access Point",
                        "will keep the chunk loaded even when no players are nearby.")
                .define("chunkLoadingEnabled", true);
        builder.pop();
    }

    static {
        Pair<AEInfinityBoosterConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(AEInfinityBoosterConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
    *///?}
    //? if fabric {
    /*public static final Supplier<Integer> DIMENSION_CARD_DRAIN = () -> get().dimensionCardDrain;
    public static final Supplier<Integer> INFINITY_CARD_DRAIN = () -> get().infinityCardDrain;
    public static final Supplier<Boolean> CHUNK_LOADING_ENABLED = () -> get().chunkLoadingEnabled;

    @ConfigEntry.Gui.Tooltip
    @Comment("The amount of power the Dimension Card drains per tick")
    public int dimensionCardDrain = 100;

    @ConfigEntry.Gui.Tooltip
    @Comment("The amount of power the Infinity Card drains per tick")
    public int infinityCardDrain = 50;

    @ConfigEntry.Gui.Tooltip
    @Comment("If true, inserting a Dimension Card or Infinity Card into a Wireless Access Point will keep the chunk loaded even when no players are nearby.")
    public boolean chunkLoadingEnabled = true;

    public static void register() {
        AutoConfig.register(AEInfinityBoosterConfig.class, JanksonConfigSerializer::new);
    }

    private static AEInfinityBoosterConfig get() {
        return AutoConfig.getConfigHolder(AEInfinityBoosterConfig.class).getConfig();
    }

    @Override
    public void validatePostLoad() {
        dimensionCardDrain = Math.max(0, dimensionCardDrain);
        infinityCardDrain = Math.max(0, infinityCardDrain);
    }
    *///?}
}
