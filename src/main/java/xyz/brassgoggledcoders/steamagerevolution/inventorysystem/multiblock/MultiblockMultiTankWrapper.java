package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.MultiFluidHandler;

//TODO This shouldn't need to exist
public class MultiblockMultiTankWrapper implements IFluidHandler {

    final MultiblockInventoryTileEntity<?> tile;
    final String name;

    public MultiblockMultiTankWrapper(MultiblockInventoryTileEntity<?> tile, String name) {
        this.tile = tile;
        this.name = name;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler(name, MultiFluidHandler.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler(name, MultiFluidHandler.class)
                    .fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler(name, MultiFluidHandler.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler(name, MultiFluidHandler.class)
                    .drain(maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        if(tile.isConnected()
                && tile.getMultiblockController().getInventory().getHandler(name, MultiFluidHandler.class) != null) {
            return tile.getMultiblockController().getInventory().getHandler(name, MultiFluidHandler.class)
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
