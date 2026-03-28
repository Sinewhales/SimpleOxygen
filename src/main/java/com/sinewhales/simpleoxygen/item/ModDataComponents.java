package com.sinewhales.simpleoxygen.item;

import com.sinewhales.simpleoxygen.SimpleOxygen;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, SimpleOxygen.MODID);

      // Oxygen Storage
    public static final Supplier<DataComponentType<Integer>> OXYGEN_LEVEL =
              DATA_COMPONENTS.register("oxygen_level",
                      () -> DataComponentType.<Integer>builder()
                              .persistent(Codec.INT)
                              .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.INT)
                              .build()
              );

    public static void register (IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);

    }
}
