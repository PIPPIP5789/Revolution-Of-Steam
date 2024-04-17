package xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.tileentities;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.multiblocksystem.MultiblockControllerBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.SARMultiblockTileBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.ControllerTank;

public abstract class TileEntityTankPart extends SARMultiblockTileBase<ControllerTank> {

    @Override
    public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
        return ControllerTank.class;
    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new ControllerTank(getWorld());
    }

    // TODO
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        if(isConnected()) {
            ControllerTank controller = getMultiblockController();
            nbttagcompound.setTag("tank", controller.tank.writeToNBT(new NBTTagCompound()));
        }
        return new SPacketUpdateTileEntity(pos, 3, nbttagcompound);
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
        if(isConnected()) {
            ControllerTank controller = getMultiblockController();
            nbt.setTag("tank", controller.tank.writeToNBT(new NBTTagCompound()));
        }
        return nbt;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        if(isConnected()) {
            ControllerTank controller = getMultiblockController();
            controller.tank.readFromNBT(pkt.getNbtCompound().getCompoundTag("tank"));
        }
    }
}
