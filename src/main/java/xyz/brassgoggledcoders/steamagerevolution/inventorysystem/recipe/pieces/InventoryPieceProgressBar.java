package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryPiece;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;

public class InventoryPieceProgressBar extends InventoryPiece<InventoryCraftingMachine> {

    public InventoryPieceProgressBar(int xPos, int yPos) {
        super(xPos, yPos, 22, 166, 22, 15, 0);
        // TODO Formal subpiece system
        // FIXME Broken by the change to builders
        // new InventoryPieceRecipeError(xPos + 10, yPos - 10);
    }

    @Override
    public void backgroundLayerCallback(GUIInventory gui, float partialTicks, int mouseX, int mouseY) {
        if(enclosingInv.getCurrentRecipe() != null) {
            gui.mc.renderEngine.bindTexture(GUIInventory.guiTexture);
            int progress = enclosingInv.getCurrentTicks();
            int total = enclosingInv.getCurrentRecipe().getTicksPerOperation();
            int progressScaled = progress != 0 && total != 0 ? progress * getGUIElement().width / total : 0;
            gui.drawTexturedModalRect(gui.getGuiLeft() + getX(), gui.getGuiTop() + getY(), 22, 181, progressScaled, 16);
        }
    }

    @Override
    public List<String> getTooltip(List<String> tips) {
        if(enclosingInv.getCurrentRecipe() == null) {
            tips.add(TextFormatting.RED.toString()
                    + new TextComponentTranslation("sar.recipeerror.norecipe").getFormattedText());
            if(Loader.isModLoaded("jei")) {
                tips.add(TextFormatting.RESET + "("
                        + new TextComponentTranslation("jei.tooltip.show.recipes").getFormattedText() + ")");
            }
        }
        else {
            if(GuiScreen.isShiftKeyDown()) {
                tips.add(enclosingInv.getCurrentTicks() + "/" + enclosingInv.getCurrentRecipe().getTicksPerOperation()
                        + " " + new TextComponentTranslation("info.ticks").getFormattedText());
            }
            else {
                tips.add(((double) enclosingInv.getCurrentTicks() / 20) + "/"
                        + ((double) enclosingInv.getCurrentRecipe().getTicksPerOperation() / 20) + " "
                        + new TextComponentTranslation("info.seconds").getFormattedText());
            }
        }
        return tips;
    }

    @Override
    public void mouseClickedCallback(GUIInventory inventory, int mouseButton) {
        if(Loader.isModLoaded("jei") && SARJEIPlugin.recipesGui != null) {
            SARJEIPlugin.recipesGui.showCategories(Lists.newArrayList(
                    SteamAgeRevolution.MODID + ":" + enclosingInv.enclosingMachine.getMachineType().getUID()));
        }
    }
}