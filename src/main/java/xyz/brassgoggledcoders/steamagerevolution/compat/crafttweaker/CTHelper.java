/*
package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CTHelper {
    public static FluidStack toFluidStack(ILiquidStack iStack) {
        if(iStack == null) {
            return null;
        }
        return (FluidStack) iStack.getInternal();
    }

    public static ItemStack toItemStack(IItemStack iStack) {
        if(iStack == null) {
            return ItemStack.EMPTY;
        }
        return (ItemStack) iStack.getInternal();
    }

    public static Object toObject(IIngredient iStack) {
        if(iStack == null) {
            return null;
        }
        else {
            if(iStack instanceof IOreDictEntry) {
                return ((IOreDictEntry) iStack).getName();
            }
            else if(iStack instanceof IItemStack) {
                return toItemStack((IItemStack) iStack);
            }
        }
        return null;
    }
}
*/
