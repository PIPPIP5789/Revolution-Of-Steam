package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketCardPunch;

public class GuiCardPuncher extends GuiContainer {

    private static ResourceLocation guiTexture = new ResourceLocation(SteamAgeRevolution.MODID,
            "textures/gui/card_puncher.png");
    private TileEntityCardPuncher tile;

    public GuiCardPuncher(TileEntityCardPuncher tile, EntityPlayer entityPlayer) {
        super(new ContainerCardPuncher(tile, entityPlayer));

        this.tile = tile;
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

    @Override
    public void initGui() {
        buttonList.clear();
        this.addButton(new GuiButton(0, 204, 90, 40, 20, "Punch"));
        super.initGui();
    }

    // TODO JEI Ghost item support
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.enabled) {
            SteamAgeRevolution.instance.getPacketHandler().sendToServer(new PacketCardPunch(tile.getPos()));
        }
    }
}
