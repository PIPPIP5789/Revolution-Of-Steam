/*
package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.steamagerevolution.Distiller")
public class DistillerTweaker {
    @ZenMethod
    public static void addRecipe(ILiquidStack input, IItemStack outputStack, ILiquidStack output, int craftTime,
            int steamCost) {
        MachineTweaker.addRecipe("distiller", null, new ILiquidStack[] { input }, new IItemStack[] { outputStack },
                new ILiquidStack[] { output }, craftTime, steamCost);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack outputStack, ILiquidStack output) {
        MachineTweaker.removeRecipe("distiller", new ItemStack[] { CTHelper.toItemStack(outputStack) },
                new FluidStack[] { CTHelper.toFluidStack(output) });
    }

    @ZenMethod
    public static void removeAll() {
        MachineTweaker.removeAllRecipesFor("distiller");
    }
}
*/
