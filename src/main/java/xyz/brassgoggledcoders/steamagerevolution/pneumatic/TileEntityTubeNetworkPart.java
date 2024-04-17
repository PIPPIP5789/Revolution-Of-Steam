package xyz.brassgoggledcoders.steamagerevolution.pneumatic;

import com.teamacronymcoders.base.multiblocksystem.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblocksystem.MultiblockTileEntityBase;

public class TileEntityTubeNetworkPart extends MultiblockTileEntityBase<ControllerTubeNetwork> {
    // implements IMultiblockMachineTile {

    @Override
    public Class<ControllerTubeNetwork> getMultiblockControllerType() {
        return ControllerTubeNetwork.class;
    }

    @Override
    public void onMachineAssembled(MultiblockControllerBase multiblockControllerBase) {

    }

    @Override
    public void onMachineBroken() {

    }

    @Override
    public void onMachineActivated() {

    }

    @Override
    public void onMachineDeactivated() {

    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new ControllerTubeNetwork(getWorld());
    }

    // Don't refresh the TE when parts are rotated TODO - this allows pipes to be
    // rotated without breaking the multiblock
    // @Override
    // public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState,
    // IBlockState newState) {
    // return oldState.getBlock() != newState.getBlock();
    // }
}
