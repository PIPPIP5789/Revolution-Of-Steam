package xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.tileentities;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

public class TileEntityCrucibleCasing extends TileEntityCruciblePart {

    public TileEntityCrucibleCasing() {

    }

    @Override
    public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForSides(IMultiblockValidator validatorCallback) {

        return true;
    }

    @Override
    public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {

        return true;
    }

    @Override
    public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {

        return false;
    }
}
