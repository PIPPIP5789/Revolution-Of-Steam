package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFluidContainer extends SlotItemHandler {
    public SlotFluidContainer(IItemHandler itemHandler, int index, int xPos, int yPos) {
        super(itemHandler, index, xPos, yPos);
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return stack != null && stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    }
}