package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock;

import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;

public class MultiblockSteamWrapper implements IFluidTank, IFluidHandler {

    final MultiblockInventoryTileEntity<? extends MultiblockCraftingMachine<?>> tile;

    // TODO Why can't the second type be bounded like on inventory wrapper?
    public MultiblockSteamWrapper(MultiblockInventoryTileEntity<? extends MultiblockCraftingMachine<?>> tile) {
        this.tile = tile;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class)
                    .fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class)
                    .drain(maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack getFluid() {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class)
                    .getFluid();
        }
        return null;
    }

    @Override
    public int getFluidAmount() {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class)
                    .getFluidAmount();
        }
        return 0;
    }

    @Override
    public int getCapacity() {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class)
                    .getCapacity();
        }
        return 0;
    }

    @Override
    public FluidTankInfo getInfo() {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class).getInfo();
        }
        return null;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class)
                    .getTankProperties();
        }
        return new IFluidTankProperties[0];
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler("steamTank", FluidTankSync.class)
                    .drain(resource, doDrain);
        }
        return null;
    }

}
