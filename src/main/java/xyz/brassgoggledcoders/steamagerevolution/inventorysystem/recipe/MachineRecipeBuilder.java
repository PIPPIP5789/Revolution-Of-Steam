package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.util.inventory.IngredientFluidStack;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreIngredient;

public class MachineRecipeBuilder {
    public String crafter;
    public Ingredient[] itemInputs;
    public IngredientFluidStack[] fluidInputs;
    public ItemStack[] itemOutputs;
    public FluidStack[] fluidOutputs;
    public int ticksToProcess, steamUsePerCraft, temperatureThreshold = 0;

    public MachineRecipeBuilder(String crafter) {
        this.crafter = crafter;
    }

    public MachineRecipeBuilder setFluidInputs(FluidStack... fluids) {
        if(fluids == null) {
            return this;
        }
        ArrayList<IngredientFluidStack> ingredients = Lists.newArrayList();
        for(FluidStack fs : fluids) {
            ingredients.add(new IngredientFluidStack(fs));
        }
        fluidInputs = ingredients.toArray(new IngredientFluidStack[ingredients.size()]);
        return this;
    }

    public MachineRecipeBuilder setItemInputs(Object... items) {
        if(items == null) {
            return this;
        }
        ArrayList<Ingredient> ingredients = Lists.newArrayList();
        for(Object input : items) {
            if(input instanceof String) {
                ingredients.add(new OreIngredient((String) input));
            }
            else {
                ingredients.add(Ingredient.fromStacks((ItemStack) input));
            }
        }
        itemInputs = ingredients.toArray(new Ingredient[ingredients.size()]);
        return this;
    }

    public MachineRecipeBuilder setFluidOutputs(FluidStack... fluid) {
        if(fluid != null) {
            fluidOutputs = fluid;
        }
        return this;
    }

    public MachineRecipeBuilder setItemOutputs(ItemStack... items) {
        if(items != null) {
            itemOutputs = items;
        }
        return this;
    }

    public MachineRecipeBuilder setCraftTime(int time) {
        ticksToProcess = time;
        return this;
    }

    public MachineRecipeBuilder setSteamCost(int use) {
        steamUsePerCraft = use;
        return this;
    }

    public MachineRecipeBuilder setTemperatureThreshold(int threshold) {
        temperatureThreshold = threshold;
        return this;
    }

    public MachineRecipe build() {
        validate();
        MachineRecipe recipe = new MachineRecipe(crafter, itemInputs, fluidInputs, ticksToProcess, steamUsePerCraft,
                itemOutputs, fluidOutputs, temperatureThreshold);
        RecipeRegistry.addRecipe(crafter, recipe);
        return recipe;
    }

    private void validate() {
        if(ArrayUtils.isEmpty(itemInputs) && ArrayUtils.isEmpty(fluidInputs)) {
            throw new IllegalArgumentException("Recipe must have at least one input");
        }
        if(ArrayUtils.isEmpty(itemInputs) && ArrayUtils.isEmpty(fluidInputs)) {
            throw new IllegalArgumentException("Recipe must have at least one output");
        }
    }
}