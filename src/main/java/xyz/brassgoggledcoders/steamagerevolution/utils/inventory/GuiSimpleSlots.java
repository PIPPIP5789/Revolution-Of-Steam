package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Deprecated
@SideOnly(Side.CLIENT)
public class GuiSimpleSlots extends GuiContainer {
    private ResourceLocation guiTexture;

    public GuiSimpleSlots(EntityPlayer player, TileEntity tile, int slots) {
        super(new ContainerSimpleSlots(player, tile, slots));
        guiTexture = new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/slot_" + slots + ".png");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.renderEngine.bindTexture(guiTexture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

}
