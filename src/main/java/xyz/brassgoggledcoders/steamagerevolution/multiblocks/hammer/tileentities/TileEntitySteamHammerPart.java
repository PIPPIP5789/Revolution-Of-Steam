package xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.tileentities;

import com.teamacronymcoders.base.multiblocksystem.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.ControllerSteamHammer;

public class TileEntitySteamHammerPart extends MultiblockInventoryTileEntity<ControllerSteamHammer> {

    @Override
    public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
        return ControllerSteamHammer.class;
    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new ControllerSteamHammer(getWorld());
    }
}
