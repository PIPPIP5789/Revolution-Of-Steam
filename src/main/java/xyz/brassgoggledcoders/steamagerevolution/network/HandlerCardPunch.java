package xyz.brassgoggledcoders.steamagerevolution.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.tileentities.TileEntityCardPuncher;

public class HandlerCardPunch implements IMessageHandler<PacketCardPunch, IMessage> {
    public HandlerCardPunch() {

    }

    @Override
    public IMessage onMessage(PacketCardPunch message, MessageContext ctx) {
        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        WorldServer worldServer = serverPlayer.getServerWorld();
        worldServer.addScheduledTask(() -> {
            BlockPos pos = message.pos;
            if(worldServer.isBlockLoaded(pos, false)) {
                TileEntity te = worldServer.getTileEntity(message.pos);
                ((TileEntityCardPuncher) te).doPunch();
            }
        });
        return null;
    }
}
