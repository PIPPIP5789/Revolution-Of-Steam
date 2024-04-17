package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import mezz.jei.api.*;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.RecipeRegistry;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;

@JEIPlugin
public class SARJEIPlugin implements IModPlugin {

    public static ITooltipCallback<FluidStack> fluidTooltipCallback = new SARFluidTooltipCallback();

    public static IDrawable tankBackground;

    public static IRecipesGui recipesGui;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        SARRecipeCategory.setGuiHelper(registry.getJeiHelpers().getGuiHelper());
        tankBackground = registry.getJeiHelpers().getGuiHelper().createDrawable(
                new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/inventory.png"), 176, 8, 20, 60);
        MachineType.machinesList.values().stream().filter(machine -> machine instanceof IHasInventory)
                .filter(machine -> ((IHasInventory<?>) machine).getInventory() instanceof InventoryCraftingMachine)
                // TODO This isn't unchecked...
                .forEach(machine -> registry
                        .addRecipeCategories(new SARRecipeCategory((IHasInventory<InventoryCraftingMachine>) machine)));
    }

    @Override
    public void register(IModRegistry registry) {
        MachineType.machinesList.forEach((uid, instance) -> add(registry, instance.getUID(), instance.getCatalyst()));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        SARJEIPlugin.recipesGui = jeiRuntime.getRecipesGui();
    }

    private void add(IModRegistry registry, String id, ItemStack catalyst) {
        if(RecipeRegistry.getRecipeMasterlist().containsKey(id)) {
            registry.addRecipes(RecipeRegistry.getRecipesForMachine(id), SteamAgeRevolution.MODID + ":" + id);
            registry.addRecipeCatalyst(catalyst, SteamAgeRevolution.MODID + ":" + id);
        }
    }
}