package xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.tileentities;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

public class TileEntityDistillerFrame extends TileEntityDistillerPart {

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
