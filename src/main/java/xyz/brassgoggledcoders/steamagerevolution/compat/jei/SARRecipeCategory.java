package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MachineRecipe;

public class SARRecipeCategory implements IRecipeCategory<MachineRecipe> {

    protected static IGuiHelper helper;

    IHasInventory<InventoryCraftingMachine> machine;
    IDrawableAnimated arrow;

    public SARRecipeCategory(IHasInventory<InventoryCraftingMachine> machine) {
        this.machine = machine;
        IDrawableStatic arrowDrawable = helper.createDrawable(
                new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/inventory.png"), 176, 83, 24, 17);
        arrow = helper.createAnimatedDrawable(arrowDrawable, 20, IDrawableAnimated.StartDirection.LEFT, false);
    }

    public static void setGuiHelper(IGuiHelper helper) {
        SARRecipeCategory.helper = helper;
    }

    @Override
    public String getUid() {
        return SteamAgeRevolution.MODID + ":" + machine.getMachineType().getUID();
    }

    @Override
    public String getTitle() {
        return machine.getMachineType().getLocalizedName();
    }

    @Override
    public String getModName() {
        return SteamAgeRevolution.MODNAME;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/inventory.png"), 7, 4,
                162, 79);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        if(machine instanceof IHasInventory) {
            IHasInventory<?> inventory = machine;
            inventory
                    .getInventory().inventoryPieces
                            .forEach(
                                    (name, piece) -> helper
                                            .createDrawable(
                                                    new ResourceLocation(SteamAgeRevolution.MODID,
                                                            "textures/gui/inventory.png"),
                                                    piece.getGUIElement().textureX, piece.getGUIElement().textureY,
                                                    piece.getGUIElement().width, piece.getGUIElement().height)
                                            .draw(minecraft, piece.getX(), piece.getY()));
        }
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MachineRecipe recipeWrapper, IIngredients ingredients) {
        int slotIndex = 0;
        for(InventoryPieceFluidTank tank : machine.getInventory()
                .getInventoryPiecesOfType(InventoryPieceFluidTank.class)) {
            boolean isInput = IOType.INPUT.equals(tank.getType());
            recipeLayout.getFluidStacks().init(slotIndex, isInput, tank.getX(), tank.getY());
            if(isInput) {
                if(ingredients.getInputs(VanillaTypes.FLUID).size() > slotIndex) {
                    recipeLayout.getFluidStacks().set(slotIndex,
                            ingredients.getInputs(VanillaTypes.FLUID).get(slotIndex));
                }
            }
            else {
                if(ingredients.getOutputs(VanillaTypes.FLUID).size() > slotIndex) { // TODO
                    recipeLayout.getFluidStacks().set(slotIndex,
                            ingredients.getOutputs(VanillaTypes.FLUID).get(slotIndex));
                }
            }
            slotIndex++;
        }
        for(InventoryPieceItemHandler handler : machine.getInventory()
                .getInventoryPiecesOfType(InventoryPieceItemHandler.class)) {
            boolean isInput = IOType.INPUT.equals(handler.getType());
            recipeLayout.getItemStacks().init(slotIndex, isInput, handler.getX(), handler.getY());
            if(isInput) {
                if(ingredients.getInputs(VanillaTypes.ITEM).size() > slotIndex) {
                    recipeLayout.getItemStacks().set(slotIndex,
                            ingredients.getInputs(VanillaTypes.ITEM).get(slotIndex));
                }
            }
            else {
                if(ingredients.getOutputs(VanillaTypes.ITEM).size() > slotIndex) {
                    recipeLayout.getItemStacks().set(slotIndex,
                            ingredients.getOutputs(VanillaTypes.ITEM).get(slotIndex));
                }
            }
            slotIndex++;
        }
    }
}
