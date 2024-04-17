package xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.tileentities;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

public class TileEntityTankWindow extends TileEntityTankPart {

    public TileEntityTankWindow() {

    }

    @Override
    public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
        return false;
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
