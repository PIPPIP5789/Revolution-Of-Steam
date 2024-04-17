package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui;

import java.io.IOException;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceMultiFluidTank;

@SideOnly(Side.CLIENT)
public class GUIInventory extends GuiContainer {
    protected final IHasInventory<? extends InventoryBasic> holder;
    protected final InventoryPlayer playerInventory;
    public static final ResourceLocation guiTexture = new ResourceLocation(SteamAgeRevolution.MODID,
            "textures/gui/inventory.png");

    public GUIInventory(EntityPlayer player, IHasInventory<? extends InventoryBasic> holder) {
        this(player, holder, new ContainerInventory(player, holder));
    }

    public GUIInventory(EntityPlayer player, IHasInventory<? extends InventoryBasic> holder,
            ContainerBase containerInstance) {
        super(containerInstance);
        this.holder = holder;
        playerInventory = player.inventory;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        for(InventoryPiece<?> piece : holder.getInventory().getInventoryPieces()) {
            GUIElement element = piece.getGUIElement();
            // TODO Don't just pass a fresh list in each time.
            // TODO Add mouse params to getTooltip
            if(!piece.getTooltip(Lists.newArrayList()).isEmpty()
                    && isPointInRegion(piece.getX(), piece.getY(), element.width, element.height, mouseX, mouseY)) {
                this.drawHoveringText(piece.getTooltip(Lists.newArrayList()), mouseX, mouseY);
            }
            piece.drawScreenCallback(this, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(holder.getMachineType().getLocalizedName(), 8, 6, 4210752);
        fontRenderer.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.renderEngine.bindTexture(guiTexture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        for(InventoryPiece<?> piece : holder.getInventory().getInventoryPieces()) {
            GUIElement element = piece.getGUIElement();
            if(piece.shouldRender()) {
                // TODO Generic handling for multi textured peices
                if(piece instanceof InventoryPieceItemHandler) {
                    InventoryPieceItemHandler pieceH = (InventoryPieceItemHandler) piece;
                    for(int slot = 0; slot < pieceH.getHandler().getSlots(); slot++) {
                        this.drawTexturedModalRect(getGuiLeft() + pieceH.getSlotPositionX(slot) + pieceH.getOffset(),
                                getGuiTop() + pieceH.getSlotPositionY(slot) + pieceH.getOffset(), element.textureX,
                                element.textureY, element.width, element.height);
                    }
                }
                else if(piece instanceof InventoryPieceMultiFluidTank) {
                    InventoryPieceMultiFluidTank pieceH = (InventoryPieceMultiFluidTank) piece;
                    for(int slot = 0; slot < pieceH.getHandler().containedTanks.length; slot++) {
                        this.drawTexturedModalRect(getGuiLeft() + pieceH.tankXs[slot] + pieceH.getOffset(),
                                getGuiTop() + pieceH.tankYs[slot] + pieceH.getOffset(), element.textureX,
                                element.textureY, element.width, element.height);
                    }
                }
                else {
                    this.drawTexturedModalRect(getGuiLeft() + piece.getX() + piece.getOffset(),
                            getGuiTop() + piece.getY() + piece.getOffset(), element.textureX, element.textureY,
                            element.width, element.height);
                }
            }
            piece.backgroundLayerCallback(this, partialTicks, mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(InventoryPiece<?> piece : holder.getInventory().getInventoryPieces()) {
            GUIElement element = piece.getGUIElement();
            if(isPointInRegion(piece.getX(), piece.getY(), element.width, element.height, mouseX, mouseY)) {
                piece.mouseClickedCallback(this, mouseButton);
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    // Elevate to public for use in callbacks
    @Override
    public boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
        return super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
    }

    // As above
    @Override
    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        super.drawGradientRect(left, top, right, bottom, startColor, endColor);
    }

    // This took me a surprisngly long time. To be fair, I did get a U in Math.
    // And apparently English too...
    public static Pair<int[], int[]> getGUIPositionGrid(int xStart, int yStart, int xSize, int ySize) {
        int[] xPositions = new int[xSize * ySize];
        int[] yPositions = new int[xSize * ySize];
        for(int vertical = 0; vertical < ySize; ++vertical) {
            for(int horizontal = 0; horizontal < xSize; ++horizontal) {
                xPositions[horizontal + (vertical * xSize)] = xStart + (horizontal * 18);
                yPositions[horizontal + (vertical * xSize)] = yStart + (vertical * 18);
            }
        }
        return Pair.of(xPositions, yPositions);
    }
}
