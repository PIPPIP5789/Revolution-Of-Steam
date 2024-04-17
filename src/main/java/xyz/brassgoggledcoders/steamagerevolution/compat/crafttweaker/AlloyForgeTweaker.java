/*
package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.steamagerevolution.AlloyForge")
public class AlloyForgeTweaker {

    @ZenMethod
    public static void addRecipe(ILiquidStack input, ILiquidStack input2, ILiquidStack output, int craftTime) {
        MachineTweaker.addRecipe("alloy forge", null, new ILiquidStack[] { input, input2 }, null,
                new ILiquidStack[] { output }, craftTime, 0);
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack output) {
        MachineTweaker.removeRecipe("alloy forge", null, new FluidStack[] { CTHelper.toFluidStack(output) });
    }

    @ZenMethod
    public static void removeAll() {
        MachineTweaker.removeAllRecipesFor("alloy forge");
    }

}
*/
