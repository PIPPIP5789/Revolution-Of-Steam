package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import java.util.List;

import com.teamacronymcoders.base.util.GuiHelper;

import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIElement;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network.PacketGUITankInteract;

public class InventoryPieceFluidTank extends InventoryPieceHandler<FluidTankSync> {

    public InventoryPieceFluidTank(IOType type, FluidTankSync handler, int xPosition, int yPosition) {
        this(type, handler, xPosition, yPosition, 2, 168, 18, 44, -1);
        if(IOType.POWER.equals(type)) {
            guiPiece = new GUIElement(0, 166, 22, 48);
            offset = -3;
        }
    }

    public InventoryPieceFluidTank(FluidTankSync handler, int xPos, int yPos) {
        this(null, handler, xPos, yPos);
    }

    public InventoryPieceFluidTank(IOType input, int capacity, int xPosition, int yPosition) {
        this(null, new FluidTankSync(capacity), xPosition, yPosition);
    }

    protected InventoryPieceFluidTank(IOType type, FluidTankSync handler, int xPosition, int yPosition, int tX, int tY,
            int w, int height, int offset) {
        super(type, handler, xPosition, yPosition, tX, tY, w, height, offset);
        getHandler().setEnclosing(this);
    }

    @Override
    public void backgroundLayerCallback(GUIInventory gui, float partialTicks, int mouseX, int mouseY) {
        FluidStack stack = handler.getFluid();
        if(stack != null && stack.getFluid() != null && stack.amount > 0) {
            GuiHelper.renderGuiTank(stack, handler.getCapacity(), stack.amount, gui.getGuiLeft() + getX(),
                    gui.getGuiTop() + getY(), 16, 42);
            gui.mc.renderEngine.bindTexture(GUIInventory.guiTexture);
            gui.drawTexturedModalRect(gui.getGuiLeft() + getX(), gui.getGuiTop() + getY() - 1, 3, 214, 18, 42);
        }
    }

    @Override
    public List<String> getTooltip(List<String> tips) {
        tips.add(com.teamacronymcoders.base.util.TextUtils.representTankContents(getHandler()).getFormattedText());
        return tips;
    }

    // TODO This needs to be done with packets
    @Override
    public void mouseClickedCallback(GUIInventory inventory, int mouseButton) {
        SteamAgeRevolution.instance.getPacketHandler()
                .sendToServer(new PacketGUITankInteract(enclosingInv.enclosingMachine.getMachinePos(), getName()));
    }
}
