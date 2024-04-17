package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetRecipe implements IMessage {

    public BlockPos pos;
    public int recipeID;

    public PacketSetRecipe() {
    }

    public PacketSetRecipe(BlockPos pos, int networkID) {
        this.pos = pos;
        recipeID = networkID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        recipeID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(recipeID);
    }

}
