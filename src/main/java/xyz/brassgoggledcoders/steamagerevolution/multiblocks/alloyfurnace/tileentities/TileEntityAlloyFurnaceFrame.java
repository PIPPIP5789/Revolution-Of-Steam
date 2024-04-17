package xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.tileentities;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

public class TileEntityAlloyFurnaceFrame extends TileEntityAlloyFurnacePart {

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
