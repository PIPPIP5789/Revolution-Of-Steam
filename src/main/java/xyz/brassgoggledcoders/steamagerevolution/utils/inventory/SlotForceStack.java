package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.*;

public class SlotForceStack extends SlotItemHandler {

    public SlotForceStack(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack) {
        ItemStack maxAdd = stack.copy();
        // TODO
        int maxInput = 64;
        maxAdd.setCount(maxInput);

        IItemHandler handler = getItemHandler();
        ItemStack currentStack = handler.getStackInSlot(getSlotIndex());
        if(handler instanceof IItemHandlerModifiable) {
            IItemHandlerModifiable handlerModifiable = (IItemHandlerModifiable) handler;

            handlerModifiable.setStackInSlot(getSlotIndex(), ItemStack.EMPTY);

            ItemStack remainder = handlerModifiable.insertItem(getSlotIndex(), maxAdd, true);

            handlerModifiable.setStackInSlot(getSlotIndex(), currentStack);

            return maxInput - remainder.getCount();
        }
        else {
            ItemStack remainder = handler.insertItem(getSlotIndex(), maxAdd, true);

            int current = currentStack.getCount();
            int added = maxInput - remainder.getCount();
            return current + added;
        }
    }
}
