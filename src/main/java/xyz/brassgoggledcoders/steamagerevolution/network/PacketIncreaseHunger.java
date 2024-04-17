package xyz.brassgoggledcoders.steamagerevolution.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketIncreaseHunger implements IMessage {

    public float hunger;

    public PacketIncreaseHunger() {
    }

    public PacketIncreaseHunger(float hunger) {
        this.hunger = hunger;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        hunger = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(hunger);
    }

}