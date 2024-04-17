package xyz.brassgoggledcoders.steamagerevolution.utils;

import java.util.Comparator;

import net.minecraft.item.ItemStack;

public class StackComparator implements Comparator<ItemStack> {

    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
    }
}
