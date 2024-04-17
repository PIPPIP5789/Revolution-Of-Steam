package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIElement;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIInventory;

public abstract class InventoryPiece<INV extends InventoryBasic> {
    protected String name;
    public INV enclosingInv;
    protected GUIElement guiPiece;
    public int posX, posY;
    // Offsets texture relative to start position of inventory piece, needed for
    // tanks and slots.
    protected int offset;
    protected boolean shouldRender = true;

    // Name MUST be unique within the inventory TODO Enforcement
    // TODO Create a 'GUIPiece' subclass that holds texture and position info?
    public InventoryPiece(int xPos, int yPos, int textureX, int textureY, int xSize, int ySize, int offset) {
        this.guiPiece = new GUIElement(textureX, textureY, xSize, ySize);
        this.offset = offset;
        this.posX = xPos;
        this.posY = yPos;
    }

    // TODO This is only of any actual benefit if we make sprites singletons
    public GUIElement getGUIElement() {
        return guiPiece;
    }

    // Set by the InventoryBuilder
    public InventoryPiece<INV> setParent(INV amBuilding) {
        this.enclosingInv = amBuilding;
        return this;
    }

    // Set by the InventoryBuilder
    public InventoryPiece<INV> setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public int getOffset() {
        return offset;
    }

    // GUI Related methods below this line
    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public boolean shouldRender() {
        return shouldRender;
    }

    public void setShouldRender(boolean shouldRender) {
        this.shouldRender = shouldRender;
    }

    public List<String> getTooltip(List<String> tips) {
        return tips;
    }

    public void drawScreenCallback(GUIInventory gui, int mouseX, int mouseY, float partialTicks) {
        // NO-OP
    }

    public void backgroundLayerCallback(GUIInventory gui, float partialTicks, int mouseX, int mouseY) {
        // NO-OP
    }

    public void mouseClickedCallback(GUIInventory guiInventory, int mouseButton) {
        // NO-OP
    }
}