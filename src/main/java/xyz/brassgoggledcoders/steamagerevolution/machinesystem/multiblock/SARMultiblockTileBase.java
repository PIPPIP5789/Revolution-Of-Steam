package xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock;

import com.teamacronymcoders.base.multiblocksystem.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;

public abstract class SARMultiblockTileBase<T extends SARMultiblockBase> extends RectangularMultiblockTileEntityBase<T>
        implements IMultiblockMachineTile {

    @Override
    public final IMultiblockMachine getControllerInfo() {
        return (IMultiblockMachine) MachineType.machinesList.get(getMultiblockControllerType());
    }

    @Override
    public boolean[] getValidPositions() {
        return new boolean[] { isGoodForFrame(null), isGoodForSides(null), isGoodForTop(null), isGoodForBottom(null),
                isGoodForInterior(null) };
    }

    @Override
    public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    public void onMachineActivated() {
    }

    @Override
    public void onMachineDeactivated() {
    }
}
