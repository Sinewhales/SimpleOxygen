package com.sinewhales.simpleoxygen.item;

import com.sinewhales.simpleoxygen.SimpleOxygen;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(SimpleOxygen.MODID);

    public static final DeferredItem<OxygenGear> OXYGEN_GEAR =
            ITEMS.register("oxygen_gear",
    () -> new OxygenGear(
            new Item.Properties().stacksTo(1)
    )
            );

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

}
