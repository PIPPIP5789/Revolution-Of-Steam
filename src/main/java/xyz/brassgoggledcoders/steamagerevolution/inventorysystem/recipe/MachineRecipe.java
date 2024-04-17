package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import java.util.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.util.inventory.IngredientFluidStack;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryPiece;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class MachineRecipe implements IRecipeWrapper {
    @Nullable
    protected final Ingredient[] itemIngredients;
    @Nullable
    protected final IngredientFluidStack[] fluidIngredients;
    protected final int ticksToProcess;
    protected final int steamUsePerCraft;
    protected final int temperatureThreshold;
    @Nullable
    protected final ItemStack[] itemOutputs;
    @Nullable
    protected final FluidStack[] fluidOutputs;
    @Nonnull
    private final String crafter;
    @Nonnull
    public int networkID;

    public MachineRecipe(String crafter, Ingredient[] itemInputs, IngredientFluidStack[] fluidInputs,
            int ticksToProcess, int steamUsePerCraft, ItemStack[] itemOutputs, FluidStack[] fluidOutputs,
            int temperatureThreshold) {
        this.crafter = crafter;
        itemIngredients = itemInputs;
        fluidIngredients = fluidInputs;
        this.ticksToProcess = ticksToProcess;
        this.steamUsePerCraft = steamUsePerCraft;
        this.temperatureThreshold = temperatureThreshold;
        this.itemOutputs = itemOutputs;
        this.fluidOutputs = fluidOutputs;
    }

    protected MachineRecipe setNetworkID(int networkID) {
        this.networkID = networkID;
        return this;
    }

    public Ingredient[] getItemInputs() {
        return itemIngredients;
    }

    public IngredientFluidStack[] getFluidInputs() {
        return fluidIngredients;
    }

    public ItemStack[] getItemOutputs() {
        return itemOutputs;
    }

    public FluidStack[] getFluidOutputs() {
        return fluidOutputs;
    }

    public String getCrafter() {
        return crafter;
    }

    public int getTicksPerOperation() {
        return ticksToProcess;
    }

    public int getSteamUsePerCraft() {
        return steamUsePerCraft;
    }

    @Optional.Method(modid = "jei")
    @Override
    public void getIngredients(IIngredients ingredients) {
        if(ArrayUtils.isNotEmpty(fluidIngredients)) {
            List<FluidStack> fluids = new ArrayList<>();
            for(IngredientFluidStack fs : fluidIngredients) {
                fluids.add(fs.getFluid());
            }
            ingredients.setInputs(VanillaTypes.FLUID, fluids);
        }
        if(ArrayUtils.isNotEmpty(itemIngredients)) {
            ArrayList<List<ItemStack>> items = Lists.newArrayList();
            for(Ingredient ing : itemIngredients) {
                items.add(Arrays.asList(ing.getMatchingStacks())); // TODO
            }
            ingredients.setInputLists(VanillaTypes.ITEM, items);
        }
        if(ArrayUtils.isNotEmpty(fluidOutputs)) {
            ingredients.setOutputs(VanillaTypes.FLUID, Arrays.asList(fluidOutputs));
        }
        if(ArrayUtils.isNotEmpty(itemOutputs)) {
            ingredients.setOutputs(VanillaTypes.ITEM, Arrays.asList(itemOutputs));
        }
    }

    @Optional.Method(modid = "jei")
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> tips = new ArrayList<>();
        for(InventoryPiece<?> piece : ((IHasInventory<InventoryCraftingMachine>) MachineType.machinesList.get(crafter))
                .getInventory().getInventoryPieces()) {
            if(piece instanceof InventoryPieceProgressBar) {
                if(isPointInRegion(piece.getX(), piece.getY(), piece.getGUIElement().width,
                        piece.getGUIElement().height, mouseX, mouseY)) {
                    tips.add("Ticks to complete: " + ticksToProcess);
                }
            }
        }
        return tips;
    }

    protected static boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX,
            int pointY) {
        return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1
                && pointY < rectY + rectHeight + 1;
    }
}
