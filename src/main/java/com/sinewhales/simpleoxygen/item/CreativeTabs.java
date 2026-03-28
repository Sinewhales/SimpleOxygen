package com.sinewhales.simpleoxygen.item;

import com.sinewhales.simpleoxygen.SimpleOxygen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SimpleOxygen.MODID);

    public static final Supplier<CreativeModeTab> SIMPLE_OXYGEN_TAB =
            CREATIVE_MODE_TABS.register("simple_oxygen_tab",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("creativetab.simpleoxygen_simple_oxygen_tab"))
                            .icon(() -> OxygenGear.fullStack())
                            .displayItems((parameters, output) -> {
                                // Empty
                                output.accept(new ItemStack(ModItems.OXYGEN_GEAR.get()));
                                // Full
                                output.accept(OxygenGear.fullStack());

                            })
                                            .build()
                            );

    public static void register(IEventBus modEventBus){
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
