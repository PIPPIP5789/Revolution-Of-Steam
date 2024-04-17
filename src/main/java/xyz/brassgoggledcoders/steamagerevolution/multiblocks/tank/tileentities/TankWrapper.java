package xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.tileentities;

import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TankWrapper implements IFluidTank, IFluidHandler {

    final TileEntityTankPart tile;

    public TankWrapper(TileEntityTankPart tile) {
        this.tile = tile;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(tile.isConnected() && tile.getMultiblockController().tank != null) {
            return tile.getMultiblockController().tank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if(tile.isConnected() && tile.getMultiblockController().tank != null) {
            return tile.getMultiblockController().tank.drain(maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack getFluid() {
        if(tile.isConnected() && tile.getMultiblockController().tank != null) {
            return tile.getMultiblockController().tank.getFluid();
        }
        return null;
    }

    @Override
    public int getFluidAmount() {
        if(tile.isConnected() && tile.getMultiblockController().tank != null) {
            return tile.getMultiblockController().tank.getFluidAmount();
        }
        return 0;
    }

    @Override
    public int getCapacity() {
        if(tile.isConnected() && tile.getMultiblockController().tank != null) {
            return tile.getMultiblockController().tank.getCapacity();
        }
        return 0;
    }

    @Override
    public FluidTankInfo getInfo() {
        if(tile.isConnected() && tile.getMultiblockController().tank != null) {
            return tile.getMultiblockController().tank.getInfo();
        }
        return null;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        if(tile.isConnected() && tile.getMultiblockController().tank != null) {
            return tile.getMultiblockController().tank.getTankProperties();
        }
        return new IFluidTankProperties[0];
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if(tile.isConnected() && tile.getMultiblockController().tank != null) {
            return tile.getMultiblockController().tank.drain(resource, doDrain);
        }
        return null;
    }

}
