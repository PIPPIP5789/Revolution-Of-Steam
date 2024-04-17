/*
package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.steamagerevolution.Vat")
public class VatTweaker {
    @ZenMethod
    public static void addRecipe(ILiquidStack[] input, IIngredient[] inputItems, ILiquidStack output, int craftTime) {
        assert input.length <= 3 : "Vat recipe cannot have more than three fluid inputs";
        assert inputItems.length <= 3 : "Vat recipe cannot have more than three item inputs";
        MachineTweaker.addRecipe("vat", inputItems, input, null, new ILiquidStack[] { output }, craftTime, 0);
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack output) {
        MachineTweaker.removeRecipe("vat", null, new FluidStack[] { CTHelper.toFluidStack(output) });
    }

    @ZenMethod
    public static void removeAll() {
        MachineTweaker.removeAllRecipesFor("vat");
    }
}
*/
