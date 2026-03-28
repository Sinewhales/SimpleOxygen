package com.sinewhales.simpleoxygen.client;

import com.sinewhales.simpleoxygen.Config;
import com.sinewhales.simpleoxygen.SimpleOxygen;
import com.sinewhales.simpleoxygen.item.ModItems;
import com.sinewhales.simpleoxygen.item.OxygenGear;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.minecraft.client.Minecraft;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.client.gui.GuiGraphics;

@EventBusSubscriber(modid = SimpleOxygen.MODID, value = Dist.CLIENT)
public class OxygenHudHandler {

    // Bar dimensions
    private static final int BAR_WIDTH = 64;
    private static final int BAR_HEIGHT = 4;
    private static final int BAR_Y_OFFSET = 45;

    // Render after the hotbar
    @SubscribeEvent
    public static void onRenderHotbar(RenderGuiLayerEvent.Post event){
        if (!event.getName().equals(VanillaGuiLayers.HOTBAR)) return;

        Minecraft mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;

        // Check if Dimension is Unbreathable (from AirDamageHandler / Could probably be better)
        ResourceLocation dimKey = player.level().dimension().location();
        boolean isUnbreathable = Config.UNBREATHABLE_DIMENSIONS.get()
                .stream()
                .anyMatch(id -> id.equals(dimKey.toString()));

        if (!isUnbreathable) return;

        // Get Oxygen from the curios slot
        int oxygen = 0;
        var curiosOptional = CuriosApi.getCuriosInventory(player);
        if (curiosOptional.isEmpty()) return;

        if (curiosOptional.isPresent()){
            var result = curiosOptional.get().findFirstCurio(ModItems.OXYGEN_GEAR.get());
            if (result.isPresent()){
                oxygen = OxygenGear.getOxygen(result.get().stack());
            }
        }

        // Bar fill
        float ratio = (float) oxygen / OxygenGear.MAX_OXYGEN;
        int filledWidth = (int) (BAR_WIDTH * ratio);

        // Centered
        GuiGraphics graphics = event.getGuiGraphics();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int barX = (screenWidth / 2 ) + 15;
        int barY = screenHeight - BAR_Y_OFFSET;

        // Draw background
        graphics.fill(barX, barY, barX + BAR_WIDTH, barY + BAR_HEIGHT, 0xFF333333);

        // Draw Fill
        int barColor = ratio > 0.25f ? 0xFF00BFFF : 0xFFFF3300;
        if (filledWidth > 0) {
            graphics.fill(barX, barY, barX + filledWidth, barY + BAR_HEIGHT, barColor);
        }

        graphics.fill(barX - 1, barY - 1, barX + BAR_WIDTH + 1, barY, 0xFF000000);           // top
        graphics.fill(barX - 1, barY + BAR_HEIGHT, barX + BAR_WIDTH + 1, barY + BAR_HEIGHT + 1, 0xFF000000); // bottom
        graphics.fill(barX - 1, barY - 1, barX, barY + BAR_HEIGHT + 1, 0xFF000000);           // left
        graphics.fill(barX + BAR_WIDTH, barY - 1, barX + BAR_WIDTH + 1, barY + BAR_HEIGHT + 1, 0xFF000000); // right






    }


}
