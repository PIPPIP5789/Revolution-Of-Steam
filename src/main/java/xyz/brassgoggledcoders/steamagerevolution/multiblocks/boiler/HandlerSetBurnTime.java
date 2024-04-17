package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.tileentities.TileEntityBoilerPart;

public class HandlerSetBurnTime implements IMessageHandler<PacketSetBurnTime, IMessage> {
    @Override
    public IMessage onMessage(PacketSetBurnTime message, MessageContext ctx) {
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

    private void processMessage(WorldClient worldClient, PacketSetBurnTime message) {
        TileEntity te = worldClient.getTileEntity(message.pos);
        if(te instanceof TileEntityBoilerPart && ((TileEntityBoilerPart) te).getMultiblockController() != null) {
            ((TileEntityBoilerPart) te).getMultiblockController().currentBurnTime = message.value;
        }
    }
}
