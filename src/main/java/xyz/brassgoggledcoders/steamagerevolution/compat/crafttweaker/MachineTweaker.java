/*
package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.*;

public class MachineTweaker {

    // TODO Builder
    public static void addRecipe(String machine, @Nullable IIngredient[] itemInputs,
            @Nullable ILiquidStack[] fluidInputs, @Nullable IItemStack[] itemOutputs,
            @Nullable ILiquidStack[] fluidOutputs, int timeToCraft, int steamCost) {
        Object[] itemInputsObj = null;
        FluidStack[] fluidInputsStack = null;
        ItemStack[] itemOutputsStack = null;
        FluidStack[] fluidOutputsStack = null;
        if(itemInputs != null) {
            if(Arrays.asList(itemInputs).stream().anyMatch(ingredient -> ingredient instanceof ILiquidStack)) {
                SteamAgeRevolution.instance.getLogger().error(
                        "Attempted to use a liquid ingredient where only ore/itemstack is valid. This recipe will not be added");
                return;
            }
            itemInputsObj = new Object[itemInputs.length];
            for(int i = 0; i < itemInputs.length; i++) {
                itemInputsObj[i] = CTHelper.toObject(itemInputs[i]);
            }
        }
        if(fluidInputs != null) {
            fluidInputsStack = new FluidStack[fluidInputs.length];
            for(int i = 0; i < fluidInputs.length; i++) {
                fluidInputsStack[i] = CTHelper.toFluidStack(fluidInputs[i]);
            }
        }
        if(itemOutputs != null) {
            itemOutputsStack = new ItemStack[itemOutputs.length];
            for(int i = 0; i < itemOutputs.length; i++) {
                itemOutputsStack[i] = CTHelper.toItemStack(itemOutputs[i]);
            }
        }
        if(fluidOutputs != null) {
            fluidOutputsStack = new FluidStack[fluidOutputs.length];
            for(int i = 0; i < fluidOutputsStack.length; i++) {
                fluidOutputsStack[i] = CTHelper.toFluidStack(fluidOutputs[i]);
            }
        }
        MachineRecipe r = new MachineRecipeBuilder(machine).setItemInputs(itemInputsObj)
                .setFluidInputs(fluidInputsStack).setItemOutputs(itemOutputsStack).setFluidOutputs(fluidOutputsStack)
                .setCraftTime(timeToCraft).setSteamCost(steamCost).build();
        CraftTweakerAPI.apply(new Add(machine, r));
    }

    public static void removeRecipe(String crafter, ItemStack[] itemOutputs, FluidStack[] fluidOutputs) {
        CraftTweakerAPI.apply(new Remove(crafter, itemOutputs, fluidOutputs));
    }

    protected static class Add implements IAction {
        private final String crafter;
        private final MachineRecipe recipe;

        public Add(String crafter, MachineRecipe recipe) {
            this.crafter = crafter;
            this.recipe = recipe;
        }

        @Override
        public void apply() {
            RecipeRegistry.getRecipesForMachine(crafter).add(recipe);
        }

        @Override
        public String describe() {
            return "Adding " + crafter + " Recipe"; // TODO Recipe toString()
        }
    }

    protected static class Remove implements IAction {

        private final String crafter;
        private final ItemStack[] itemOutputs;
        private final FluidStack[] fluidOutputs;

        public Remove(String machine, @Nullable ItemStack[] itemOutputs, @Nullable FluidStack[] fluidOutputs) {
            crafter = machine;
            this.itemOutputs = itemOutputs;
            this.fluidOutputs = fluidOutputs;
        }

        @Override
        public void apply() {
            ArrayList<MachineRecipe> recipeList = RecipeRegistry.getRecipesForMachine(crafter);
            for(MachineRecipe r : recipeList) {
                boolean itemOutputMatches = false;
                boolean fluidOutputMatches = false;
                // TODO Make this match more flexibly
                if(ArrayUtils.isNotEmpty(itemOutputs)) {
                    itemOutputMatches = r.getItemOutputs().equals(itemOutputs);
                }
                else {
                    itemOutputMatches = true;
                }
                if(ArrayUtils.isNotEmpty(fluidOutputs)) {
                    fluidOutputMatches = r.getFluidOutputs().equals(fluidOutputs);
                }
                else {
                    fluidOutputMatches = true;
                }
                if(itemOutputMatches && fluidOutputMatches) {
                    recipeList.remove(r);
                }
            }
        }

        @Override
        public String describe() {
            return "Removing " + crafter + " recipe";
        }

    }

    public static void removeAllRecipesFor(String machine) {
        CraftTweakerAPI.apply(new RemoveAll(machine));
    }

    protected static class RemoveAll implements IAction {

        String machine;

        public RemoveAll(String machine) {
            this.machine = machine;
        }

        @Override
        public void apply() {
            RecipeRegistry.getRecipesForMachine(machine).clear();
        }

        @Override
        public String describe() {
            return "Removing all default recipes for " + machine;
        }

    }
}
*/
