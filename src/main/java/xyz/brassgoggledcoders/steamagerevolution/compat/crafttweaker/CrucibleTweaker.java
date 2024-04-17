/*
package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.steamagerevolution.Crucible")
public class CrucibleTweaker {
    @ZenMethod
    public static void addRecipe(IIngredient input, ILiquidStack output, int craftTime, int steamCost) {
        MachineTweaker.addRecipe("crucible", new IIngredient[] { input }, null, null, new ILiquidStack[] { output },
                craftTime, steamCost);
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack output) {
        MachineTweaker.removeRecipe("crucible", null, new FluidStack[] { CTHelper.toFluidStack(output) });
    }

    @ZenMethod
    public static void removeAll() {
        MachineTweaker.removeAllRecipesFor("crucible");
    }
}
*/
