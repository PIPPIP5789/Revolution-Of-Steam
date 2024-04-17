package xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities;

import com.teamacronymcoders.base.multiblocksystem.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.ControllerVat;

public abstract class TileEntityVatPart extends MultiblockInventoryTileEntity<ControllerVat> {

    @Override
    public Class<ControllerVat> getMultiblockControllerType() {
        return ControllerVat.class;
    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new ControllerVat(getWorld());
    }

}
