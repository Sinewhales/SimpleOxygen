package com.sinewhales.simpleoxygen;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> UNBREATHABLE_DIMENSIONS;

    static {
        BUILDER.push("Air Damage");

        UNBREATHABLE_DIMENSIONS = BUILDER
                .comment("List of Dimensions IDs where the air is unbreathable.",
                        "Players without proper protection will take damage over time",
                        "Exemple : [\"minecraft:the_end\", \"minecraft:nether\"]")
                .defineListAllowEmpty(
                        "unbreathable_dimensions",
                        List.of("minecraft:the_end"),
                        entry -> entry instanceof String s && s.contains(":")
                );

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
