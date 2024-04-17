package xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder.tileentities;

import com.teamacronymcoders.base.multiblocksystem.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder.ControllerGrinder;

public abstract class TileEntityGrinderPart extends MultiblockInventoryTileEntity<ControllerGrinder> {

    @Override
    public Class<ControllerGrinder> getMultiblockControllerType() {
        return ControllerGrinder.class;
    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new ControllerGrinder(getWorld());
    }
}