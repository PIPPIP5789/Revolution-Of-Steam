package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler;

import java.util.List;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryPiece;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;

public class InventoryPieceBurnTime extends InventoryPiece<InventoryCraftingMachine> {

    public InventoryPieceBurnTime(int xPos, int yPos) {
        super(xPos, yPos, 94, 166, 13, 13, -2);
    }

    @Override
    public void backgroundLayerCallback(GUIInventory gui, float partialTicks, int mouseX, int mouseY) {
        if(enclosingInv.enclosingMachine instanceof ControllerBoiler) {
            ControllerBoiler boiler = (ControllerBoiler) enclosingInv.enclosingMachine;
            if(boiler.currentBurnTime > 0) {
                int scaled = Math.min(13, boiler.currentBurnTime * getGUIElement().height / 200);
                gui.drawTexturedModalRect(gui.getGuiLeft() + getX() + this.getOffset(), gui.getGuiTop() + getY() + this.getOffset() + (getGUIElement().height - scaled),
                        94, 179, 14, scaled);
            }
        }
        super.backgroundLayerCallback(gui, partialTicks, mouseX, mouseY);
    }

    @Override
    public List<String> getTooltip(List<String> tips) {
        if(enclosingInv.enclosingMachine instanceof ControllerBoiler) {
            ControllerBoiler boiler = (ControllerBoiler) enclosingInv.enclosingMachine;
            if(boiler.currentBurnTime > 0) {
                tips.add("Burn Time: " + boiler.currentBurnTime + " ticks");
            }
        }
        return tips;
    }

}
