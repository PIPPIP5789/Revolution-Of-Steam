package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import xyz.brassgoggledcoders.steamagerevolution.SARCaps;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryHeatable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryPiece;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIInventory;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.ControllerBoiler;

public class InventoryPieceTemperatureGauge extends InventoryPiece<InventoryHeatable> {

    public InventoryPieceTemperatureGauge(int xPos, int yPos) {
        super(xPos, yPos, 88, 166, 6, 44, -2);
    }
    
    @Override
    public void backgroundLayerCallback(GUIInventory gui, float partialTicks, int mouseX, int mouseY) {
    	super.backgroundLayerCallback(gui, partialTicks, mouseX, mouseY);
    	if(enclosingInv.getCapability(SARCaps.HEATABLE, null).getCurrentTemperature() > 0) {
            //int scaled = Math.min(getGUIElement().height,
             //       (enclosingInv.getCapability(SARCaps.HEATABLE, null).getCurrentTemperature()
             //               / enclosingInv.getCapability(SARCaps.HEATABLE, null).getMaximumTemperature())
            //                * getGUIElement().height);
    		GL11.glPushMatrix();
        	GUIInventory.drawRect(gui.getGuiLeft() + this.getX() + this.getOffset(), gui.getGuiTop() + this.getY() + this.getOffset() + 1, gui.getGuiLeft() + this.getX() + this.getGUIElement().width - 1, gui.getGuiTop() + this.getY() + this.getGUIElement().height, Color.RED.getRGB());
            GlStateManager.color(1, 1, 1);
        	GL11.glPopMatrix();
        }
    }

    @Override
    public List<String> getTooltip(List<String> tips) {
        if(enclosingInv.enclosingMachine.getInventory().hasCapability(SARCaps.HEATABLE, null)) {
            int currentTemp = enclosingInv.getCapability(SARCaps.HEATABLE, null).getCurrentTemperature();
            if(currentTemp > 0) {
                String unit = "°C";
                    tips.add("Temperature: "
                            + currentTemp + unit + "/"
                            + enclosingInv.getCapability(SARCaps.HEATABLE, null).getMaximumTemperature() + unit);
            }
            else {
                tips.add(TextFormatting.BLUE + "Cold");
            }
        }
        return super.getTooltip(tips);
    }

//    public static double celsiusToFarenheit(double celcius) {
//        return (1.8 * celcius) + 32;
//    }

}
