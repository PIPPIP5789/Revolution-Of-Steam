package xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock;

@Deprecated // FIXME
public interface IMultiblockMachineTile {
    public IMultiblockMachine getControllerInfo();

    public boolean[] getValidPositions();

    public default String getPartFunction() {
        return null;
    }
}
