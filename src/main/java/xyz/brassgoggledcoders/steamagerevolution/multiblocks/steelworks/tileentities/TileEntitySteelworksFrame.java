package xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks.tileentities;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

public class TileEntitySteelworksFrame extends TileEntitySteelworksPart {

    @Override
    public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
        return true;
    }
}
