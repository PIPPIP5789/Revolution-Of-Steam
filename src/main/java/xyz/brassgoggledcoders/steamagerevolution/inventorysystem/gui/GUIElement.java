package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui;

public final class GUIElement {
    public final int textureX, textureY;
    public final int width, height;

    public GUIElement(int textureX, int textureY, int xSize, int ySize) {
        this.textureX = textureX;
        this.textureY = textureY;
        width = xSize;
        height = ySize;
    }
}
