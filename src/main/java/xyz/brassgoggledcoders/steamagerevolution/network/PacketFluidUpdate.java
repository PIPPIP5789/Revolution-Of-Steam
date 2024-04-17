package xyz.brassgoggledcoders.steamagerevolution.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketFluidUpdate implements IMessage {

    public BlockPos pos;
    public FluidStack fluid;
    public String name;

    public PacketFluidUpdate() {
    }

    public PacketFluidUpdate(BlockPos pos, FluidStack fluid, String name) {
        this.pos = pos;
        this.fluid = fluid;
        this.name = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        NBTTagCompound tag = ByteBufUtils.readTag(buf);
        fluid = FluidStack.loadFluidStackFromNBT(tag);
        name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        NBTTagCompound tag = new NBTTagCompound();
        if(fluid != null) {
            fluid.writeToNBT(tag);
        }
        ByteBufUtils.writeTag(buf, tag);
        ByteBufUtils.writeUTF8String(buf, name);
    }

}
