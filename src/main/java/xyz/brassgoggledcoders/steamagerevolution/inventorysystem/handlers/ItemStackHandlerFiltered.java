package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public abstract class ItemStackHandlerFiltered extends ItemStackHandlerSync {

    public ItemStackHandlerFiltered(int size) {
        super(size);
    }

    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if(!canInsertItem(slot, stack)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }

    protected abstract boolean canInsertItem(int slot, ItemStack stack);

    public static class ItemStackHandlerFuel extends ItemStackHandlerFiltered {
        public ItemStackHandlerFuel(int size) {
            super(size);
        }

        @Override
        protected boolean canInsertItem(int slot, ItemStack stack) {
            return TileEntityFurnace.isItemFuel(stack);
        }
    }
}
