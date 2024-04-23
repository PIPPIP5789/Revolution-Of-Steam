package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import java.util.Arrays;
import java.util.List;

import com.teamacronymcoders.base.util.GuiHelper;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIElement;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network.PacketGUITankInteract;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.MultiFluidHandler;

public class InventoryPieceMultiFluidTank extends InventoryPieceHandler<MultiFluidHandler> {

    public int[] tankXs;
    public int[] tankYs;
    private int activeTankNum;

    public InventoryPieceMultiFluidTank(IOType type, MultiFluidHandler handler, int[] xPosition, int[] yPosition) {
        super(type, handler, xPosition[0], yPosition[0], 2, 168, 18, 44, -1);
        if(IOType.POWER.equals(type)) {
            guiPiece = new GUIElement(0, 166, 22, 48);
            offset = -3;
        }
        this.tankXs = xPosition;
        this.tankYs = yPosition;
        Arrays.asList(this.getHandler().containedTanks).forEach(tank -> tank.setEnclosing(this));
    }

    public FluidTankSync findFluidTank(int guiLeft, int guiTop, int mouseX, int mouseY) {
        for(int i = 0; i < tankXs.length; i++) {
            if(isPointInRegion(guiLeft + i * 18, guiTop, 18, 44, mouseX, mouseY)) {
                return this.getHandler().containedTanks[i];
            }
        }

        return null;
    }

    protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY)
    {
        int i = rectX;
        int j = rectY;
        pointX = pointX - i;
        pointY = pointY - j;
        return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
    }

    @Override
    public void backgroundLayerCallback(GUIInventory gui, float partialTicks, int mouseX, int mouseY) {
        int i = 0;
        for(FluidTankSync tank : this.getHandler().containedTanks) {
            FluidStack stack = tank.getFluid();
            if(stack != null && stack.getFluid() != null && stack.amount > 0) {
                GuiHelper.renderGuiTank(stack, tank.getCapacity(), stack.amount, gui.getGuiLeft() + tankXs[i],
                        gui.getGuiTop() + tankYs[i], 16, 42);
                gui.mc.renderEngine.bindTexture(GUIInventory.guiTexture);
                gui.drawTexturedModalRect(gui.getGuiLeft() + tankXs[i], gui.getGuiTop() + tankYs[i] - 1, 3, 214, 18, 42);
                i++;
            }
        }
    }

    @Override
    public List<String> getTooltip(List<String> tips) {
        // TODO
        tips.add(com.teamacronymcoders.base.util.TextUtils.representTankContents(handler.containedTanks[activeTankNum]).getFormattedText());

        return tips;
    }

    @Override
    public void mouseClickedCallback(GUIInventory inventory, int mouseButton) {
        SteamAgeRevolution.instance.getPacketHandler().sendToServer(new PacketGUITankInteract(enclosingInv.enclosingMachine.getMachinePos(), getName(), getActiveTankNum()));
        // SteamAgeRevolution.instance.getPacketHandler()
        // .sendToServer(new
        // PacketGUITankInteract(enclosingInv.enclosingMachine.getMachinePos(),
        // getName()));

        /*
        SteamAgeRevolution.instance.getPacketHandler()
                .sendToServer(new PacketGUITankInteract(enclosingInv.enclosingMachine.getMachinePos(), getName()));

         */
    }

    public void setActiveTankNum(int activeTankNum) {
        this.activeTankNum = activeTankNum;
    }

    public int getActiveTankNum() {
        return this.activeTankNum;
    }

}
