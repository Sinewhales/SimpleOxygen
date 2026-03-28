package com.sinewhales.simpleoxygen;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import com.sinewhales.simpleoxygen.Config;
import com.sinewhales.simpleoxygen.AirDamageHandler;
import com.sinewhales.simpleoxygen.item.CreativeTabs;
import com.sinewhales.simpleoxygen.item.ModDataComponents;
import com.sinewhales.simpleoxygen.item.ModItems;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;



// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SimpleOxygen.MODID)
public class SimpleOxygen {
    public static final String MODID = "simpleoxygen";
    public static final Logger LOGGER = LogUtils.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public SimpleOxygen(IEventBus modEventBus, ModContainer modContainer) {

        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModDataComponents.register(modEventBus);
        ModItems.register(modEventBus);
        CreativeTabs.register(modEventBus);

        NeoForge.EVENT_BUS.register(AirDamageHandler.class);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

        }



    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

        }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
