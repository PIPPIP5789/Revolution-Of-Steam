package xyz.brassgoggledcoders.steamagerevolution.recipes;

import java.awt.Color;
import java.util.ArrayList;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class FumeCollectorRecipe implements IRecipeWrapper {
    private static ArrayList<FumeCollectorRecipe> recipeList = new ArrayList<FumeCollectorRecipe>();
    public final ItemStack fuel;
    public final float chance;
    public final FluidStack output;

    public FumeCollectorRecipe(ItemStack fuel, FluidStack fluid, float chance) {
        this.fuel = fuel;
        if(chance > 1.0f) {
            chance = 1.0f;
        }
        this.chance = chance;
        output = fluid;
    }

    public static void addRecipe(ItemStack fuel, FluidStack fluid, float chance) {
        recipeList.add(new FumeCollectorRecipe(fuel, fluid, chance));
    }

    public static FumeCollectorRecipe getRecipe(ItemStack fuel) {
        for(FumeCollectorRecipe r : recipeList) {
            if(fuel.isItemEqual(r.fuel)) {
                return r;
            }
        }
        return null;
    }

    public static ArrayList<FumeCollectorRecipe> getRecipeList() {
        return recipeList;
    }

    @Optional.Method(modid = "jei")
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, fuel);
        ingredients.setOutput(VanillaTypes.FLUID, output);
    }

    @Override
    @Optional.Method(modid = "jei")
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString((chance * 100) + "% chance (each second)", 170, 160, Color.red.getRGB());
    }
}
