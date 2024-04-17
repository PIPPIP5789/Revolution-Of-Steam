package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetBurnTime implements IMessage {

    public BlockPos pos;
    public int value;

    public PacketSetBurnTime() {
    }

    public PacketSetBurnTime(BlockPos pos, int value) {
        this.pos = pos;
        this.value = value;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        value = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(value);
    }

}
