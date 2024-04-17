/*
package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.steamagerevolution.Steelworks")
public class SteelworksTweaker {
    @ZenMethod
    public static void addRecipe(ILiquidStack input, IIngredient input2, IItemStack output, int craftTime,
            int steamCost) {
        MachineTweaker.addRecipe("steelworks", new IIngredient[] { input2 }, new ILiquidStack[] { input },
                new IItemStack[] { output }, null, craftTime, steamCost);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MachineTweaker.removeRecipe("steelworks", new ItemStack[] { CTHelper.toItemStack(output) }, null);
    }

    @ZenMethod
    public static void removeAll() {
        MachineTweaker.removeAllRecipesFor("steelworks");
    }
}
*/
