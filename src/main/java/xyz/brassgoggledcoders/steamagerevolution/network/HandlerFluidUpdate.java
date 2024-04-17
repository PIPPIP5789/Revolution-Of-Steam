package xyz.brassgoggledcoders.steamagerevolution.network;

import com.teamacronymcoders.base.multiblocksystem.MultiblockTileEntityBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;

public class HandlerFluidUpdate implements IMessageHandler<PacketFluidUpdate, IMessage> {
    public HandlerFluidUpdate() {
    }

    @Override
    public IMessage onMessage(PacketFluidUpdate message, MessageContext ctx) {
        Minecraft minecraft = Minecraft.getMinecraft();
        final WorldClient worldClient = minecraft.world;
        minecraft.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(worldClient, message);
            }
        });
        return null;
    }

    private void processMessage(WorldClient worldClient, PacketFluidUpdate message) {
        //SteamAgeRevolution.instance.getLogger().devInfo("Fluid update received");
        TileEntity te = worldClient.getTileEntity(message.pos);
        if(te instanceof IHasInventory) {
            IHasInventory<?> tile = (IHasInventory<?>) te;
            tile.getInventory().updateFluid(message);
        }
        else {
            MultiblockTileEntityBase<?> tile = (MultiblockTileEntityBase<?>) te;
            IHasInventory<?> controller = (IHasInventory<?>) tile.getMultiblockController();
            controller.getInventory().updateFluid(message);
        }
    }
}
