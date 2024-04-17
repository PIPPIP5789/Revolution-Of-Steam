/*
package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.steamagerevolution.Grinder")
public class GrinderTweaker {
    @ZenMethod
    public static void addRecipe(IIngredient input, IItemStack output, int craftTime, int steamCost) {
        MachineTweaker.addRecipe("grinder", new IIngredient[] { input }, null, new IItemStack[] { output }, null,
                craftTime, steamCost);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MachineTweaker.removeRecipe("grinder", new ItemStack[] { CTHelper.toItemStack(output) }, null);
    }

    @ZenMethod
    public static void removeAll() {
        MachineTweaker.removeAllRecipesFor("grinder");
    }
}
*/
