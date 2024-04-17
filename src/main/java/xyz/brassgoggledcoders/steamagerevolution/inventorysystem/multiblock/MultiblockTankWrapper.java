package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock;

import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;

public class MultiblockTankWrapper implements IFluidTank, IFluidHandler {

    final MultiblockInventoryTileEntity<?> tile;
    final String name;

    // TODO Bounds
    public MultiblockTankWrapper(MultiblockInventoryTileEntity<?> tile, String name) {
        this.tile = tile;
        this.name = name;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class).fill(resource,
                    doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class).drain(maxDrain,
                    doDrain);
        }
        return null;
    }

    @Override
    public FluidStack getFluid() {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class).getFluid();
        }
        return null;
    }

    @Override
    public int getFluidAmount() {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class).getFluidAmount();
        }
        return 0;
    }

    @Override
    public int getCapacity() {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class).getCapacity();
        }
        return 0;
    }

    @Override
    public FluidTankInfo getInfo() {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class).getInfo();
        }
        return null;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class)
                    .getTankProperties();
        }
        return new IFluidTankProperties[0];
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler(name, FluidTankSync.class).drain(resource,
                    doDrain);
        }
        return null;
    }

}
