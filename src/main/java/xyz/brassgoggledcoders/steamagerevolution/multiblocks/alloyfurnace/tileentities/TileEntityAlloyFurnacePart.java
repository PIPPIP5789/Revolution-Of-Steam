package xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.tileentities;

import com.teamacronymcoders.base.multiblocksystem.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.ControllerAlloyFurnace;

public abstract class TileEntityAlloyFurnacePart extends MultiblockInventoryTileEntity<ControllerAlloyFurnace> {

    @Override
    public Class<ControllerAlloyFurnace> getMultiblockControllerType() {
        return ControllerAlloyFurnace.class;
    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new ControllerAlloyFurnace(getWorld());
    }

}
