package com.sinewhales.simpleoxygen;

import com.sinewhales.simpleoxygen.Config;
import com.sinewhales.simpleoxygen.item.ModItems;
import com.sinewhales.simpleoxygen.item.OxygenGear;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSources;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class AirDamageHandler {
    private static final int DAMAGE_INTERVAL_TICKS = 60;
    private static final float DAMAGE_AMOUNT = 1.0F;

    private static final int OXYGEN_DRAIN_INTERVAL = 1;


    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event){
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ResourceLocation dimKey = player.level().dimension().location();

        boolean isUnbreathable = Config.UNBREATHABLE_DIMENSIONS.get()
                .stream()
                .anyMatch(id -> id.equals(dimKey.toString()));

        if (!isUnbreathable) return;

        boolean oxygenProtected = false;

        var curiosOptional = CuriosApi.getCuriosInventory(player);
        if (curiosOptional.isPresent()) {
            var curiosInventory = curiosOptional.get();

            var result = curiosInventory.findFirstCurio(ModItems.OXYGEN_GEAR.get());
            if (result.isPresent()) {
                var slotResult = result.get();
                var stack = slotResult.stack();

                if (OxygenGear.hasOxygen(stack)){
                    oxygenProtected = true;

                    if (player.tickCount % OXYGEN_DRAIN_INTERVAL == 0) {
                        OxygenGear.depleteOne(stack);
                    }
                }
            }
        }

        if (!oxygenProtected && player.tickCount % DAMAGE_INTERVAL_TICKS == 0) {
            DamageSources sources = player.level().damageSources();

            player.hurt(sources.magic(), DAMAGE_AMOUNT);
        }

    }
}
