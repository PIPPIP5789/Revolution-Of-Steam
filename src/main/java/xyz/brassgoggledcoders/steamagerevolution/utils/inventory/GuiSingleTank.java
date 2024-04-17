package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import java.util.List;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.util.GuiHelper;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Deprecated
@SideOnly(Side.CLIENT)
public class GuiSingleTank extends GuiContainer {
    private static ResourceLocation guiTexture = new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/single_tank.png");
    private final IFluidTank tank;

    @Deprecated
    public GuiSingleTank(EntityPlayer player, TileEntity tile) {
        super(new ContainerSingleTank(player, tile));
        tank = (IFluidTank) tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    }

    public GuiSingleTank(EntityPlayer entityPlayer, IFluidTank tank) {
        super(new ContainerSingleTank(entityPlayer, null));
        this.tank = tank;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        if(isPointInRegion(78, 17, 20, 49, mouseX, mouseY)) {
            List<String> tooltip = Lists.newArrayList();
            tooltip.add(com.teamacronymcoders.base.util.TextUtils.representTankContents(tank).getFormattedText());
            this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.renderEngine.bindTexture(guiTexture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        FluidStack containedFluid = tank.getFluid();
        int capacity = tank.getCapacity();

        if(containedFluid != null && containedFluid.getFluid() != null && containedFluid.amount > 0) {
            GuiHelper.renderGuiTank(containedFluid, capacity, containedFluid.amount, guiLeft + 78, guiTop + 11, 20, 60);
        }

        mc.renderEngine.bindTexture(guiTexture);
        this.drawTexturedModalRect(guiLeft + 78, guiTop + 17, 176, 14, 20, 49);
    }

}
