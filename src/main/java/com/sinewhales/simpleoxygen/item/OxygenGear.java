package com.sinewhales.simpleoxygen.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class OxygenGear extends Item implements ICurioItem {
    // 6000 Ticks = 5min
    public static final int MAX_OXYGEN = 6000;

    public OxygenGear(Properties properties) {
        super(properties);
    }


    // Curio
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        // Back Slot
        return slotContext.identifier().equals("back");
    }

    public static int getOxygen(ItemStack stack){
        return stack.getOrDefault(ModDataComponents.OXYGEN_LEVEL.get(), 0);
    }

    public static void setOxygen(ItemStack stack, int amount){
        int clamped = Math.max(0, Math.min(MAX_OXYGEN, amount));
        stack.set(ModDataComponents.OXYGEN_LEVEL.get(), clamped);
    }

    public static boolean hasOxygen(ItemStack stack){
        return getOxygen(stack) > 0;
    }

    // Deplete
    public static boolean depleteOne(ItemStack stack){
       int current = getOxygen(stack);
       if (current <=0) return true;
       setOxygen(stack, current -1);
       return current - 1 <= 0;
    }

    // Charged one
    public static ItemStack fullStack(){
        ItemStack stack = new ItemStack(ModItems.OXYGEN_GEAR.get());
        setOxygen(stack, MAX_OXYGEN);
        return stack;
    }

    // Auto Equip
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()){
            var curiosOptional = CuriosApi.getCuriosInventory(player);
            if (curiosOptional.isPresent()){
                var inventory = curiosOptional.get();
                var result = inventory.findFirstCurio(ModItems.OXYGEN_GEAR.get());

                // Check if nothing is equipped
                if (result.isEmpty()){
                    var slotsOptional = inventory.getStacksHandler("back");
                    if (slotsOptional.isPresent()){
                        var slots = slotsOptional.get();

                        // Find empty
                        for (int i = 0; i < slots.getSlots(); i++){
                            if (slots.getStacks().getStackInSlot(i).isEmpty()){
                                slots.getStacks().setStackInSlot(i, stack.copy());
                                stack.shrink(1);
                                return InteractionResultHolder.success(stack);
                            }
                        }
                    }
                }
            }
        }
        return InteractionResultHolder.pass(stack);
    }


    // Tooltip
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List <Component> tooltipComponents, TooltipFlag tooltipFlag){
        int oxygen = getOxygen(stack);
        int pct = (int) ((oxygen / (float) MAX_OXYGEN) * 100);

        tooltipComponents.add(
                Component.translatable("item.simpleoxygen.oxygen_gear.tooltip.oxygen",
                        oxygen, MAX_OXYGEN, pct)
                        .withStyle(oxygen > 0 ? ChatFormatting.AQUA : ChatFormatting.RED)
        );

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            }

            // Bar
    @Override
    public boolean isBarVisible(ItemStack stack){
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack){
        return Math.round(13.0f * getOxygen(stack) / MAX_OXYGEN);
    }
    @Override
    public int getBarColor(ItemStack stack){
        return 0x00BFFF;
    }

}

