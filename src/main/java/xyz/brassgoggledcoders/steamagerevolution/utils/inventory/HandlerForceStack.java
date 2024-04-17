package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.ItemStackHandlerSync;

public class HandlerForceStack extends ItemStackHandlerSync {
    public HandlerForceStack(int slots) {
        super(slots);
    }

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
        // TODO
        if(stack.hasTagCompound() || stack.getItem().getMaxDamage(stack) > 0) {
            return 1;
        }
        return getSlotLimit(slot);
    }

    // TODO Bluuuuurgh
    @Override
    public void setStackInSlot(int from, @Nonnull ItemStack stack) {
        // If this happens on both sides wierd shit happens
        if(!enclosingIPiece.enclosingInv.enclosingMachine.getMachineWorld().isRemote) {
            for(int to = 0; to < getSlots(); to++) {
                if(getStackInSlot(to).isItemEqual(stack)) {
                    int count = getStackInSlot(to).getCount();
                    stacks.set(to, new ItemStack(stack.getItem(), stack.getCount() + count, stack.getMetadata()));
                    return;
                }
            }
        }
        super.setStackInSlot(from, stack);
    }
}
