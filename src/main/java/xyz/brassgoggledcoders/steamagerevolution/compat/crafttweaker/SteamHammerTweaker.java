/*
package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.steamagerevolution.SteamHammer")
public class SteamHammerTweaker {
    @ZenMethod
    public static void addRecipe(IIngredient input, IIngredient input2, IItemStack output, int craftTime,
            int steamCost) {
        MachineTweaker.addRecipe("steam hammer", new IIngredient[] { input, input2 }, null, new IItemStack[] { output },
                null, craftTime, steamCost);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MachineTweaker.removeRecipe("steam hammer", new ItemStack[] { CTHelper.toItemStack(output) }, null);
    }

    @ZenMethod
    public static void removeAll() {
        MachineTweaker.removeAllRecipesFor("steam hammer");
    }
}
*/
