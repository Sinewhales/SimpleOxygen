package com.sinewhales.simpleoxygen;

import com.sinewhales.simpleoxygen.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSources;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class AirDamageHandler {
    private static final int DAMAGE_INTERVAL_TICKS = 60;
    private static final float DAMAGE_AMOUNT = 0.5F;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event){
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ResourceLocation dimKey = player.level().dimension().location();

        boolean isUnbreathable = Config.UNBREATHABLE_DIMENSIONS.get()
                .stream()
                .anyMatch(id -> id.equals(dimKey.toString()));

        if (!isUnbreathable) return;

        if (player.tickCount % DAMAGE_INTERVAL_TICKS == 0) {
            DamageSources sources = player.level().damageSources();

            player.hurt(sources.magic(), DAMAGE_AMOUNT);
        }

    }
}
