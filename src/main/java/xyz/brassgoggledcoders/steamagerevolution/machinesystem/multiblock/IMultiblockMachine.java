package xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock;

import xyz.brassgoggledcoders.steamagerevolution.machinesystem.IMachine;

public interface IMultiblockMachine extends IMachine {
    @Override
    public MultiblockMachineType getMachineType();
}
