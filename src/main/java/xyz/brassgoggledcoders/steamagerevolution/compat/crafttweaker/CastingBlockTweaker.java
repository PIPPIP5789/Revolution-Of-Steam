/*
package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.steamagerevolution.CastingBlock")
public class CastingBlockTweaker {
    @ZenMethod
    public static void addRecipe(ILiquidStack input, IItemStack output, int craftTime) {
        MachineTweaker.addRecipe("casting block", null, new ILiquidStack[] { input }, new IItemStack[] { output }, null,
                craftTime, 0);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MachineTweaker.removeRecipe("casting block", new ItemStack[] { CTHelper.toItemStack(output) }, null);
    }

    @ZenMethod
    public static void removeAll() {
        MachineTweaker.removeAllRecipesFor("casting block");
    }
}
*/
