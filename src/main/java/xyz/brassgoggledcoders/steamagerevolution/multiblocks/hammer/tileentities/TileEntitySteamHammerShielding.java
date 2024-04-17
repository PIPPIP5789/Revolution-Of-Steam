package xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.tileentities;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

public class TileEntitySteamHammerShielding extends TileEntitySteamHammerPart {
    @Override
    public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
        return true;
    }
}
