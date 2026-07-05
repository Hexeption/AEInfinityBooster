package uk.co.hexeption.aeinfinitybooster.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;


public class AEInfinityBoosterConfig {

    public static final AEInfinityBoosterConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public static ModConfigSpec.IntValue DIMENSION_CARD_DRAIN;
    public static ModConfigSpec.IntValue INFINITY_CARD_DRAIN;
    public static ModConfigSpec.BooleanValue CHUNK_LOADING_ENABLED;

    private AEInfinityBoosterConfig(ModConfigSpec.Builder builder) {
        builder.push("general");
        builder.comment("General settings for AEInfinityBooster");
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
}