package xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock;

import java.util.List;
import java.util.stream.Collectors;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.multiblocksystem.IMultiblockPart;
import com.teamacronymcoders.base.multiblocksystem.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblocksystem.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblocksystem.validation.ValidationError;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public abstract class SARMultiblockBase extends RectangularMultiblockControllerBase
        implements IMultiblockMachine, IHasGui {

    protected SARMultiblockBase(World world) {
        super(world);
    }

    @Override
    protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
        List<Block> connectedBlocks = connectedParts.stream()
                .map(part -> WORLD.getBlockState(part.getWorldPosition()).getBlock()).collect(Collectors.toList());
        if(!connectedBlocks.containsAll(getMachineType().getRequiredParts())) {
            validatorCallback
                    .setLastError(new ValidationError("steamagerevolution.multiblock.validation.missingrequired",
                            getMachineType().getRequiredParts().stream()
                                    .filter(required -> !connectedBlocks.contains(required))
                                    .map(missing -> missing.getLocalizedName()).findFirst().orElse("ERROR")));// TODO
            return false;
        }

        return super.isMachineWhole(validatorCallback);
    }

    @Override
    protected void onMachineAssembled() {
        SteamAgeRevolution.instance.getLogger().devInfo("Machine Assembled");
        SteamAgeRevolution.proxy.spawnMultiblockAssemblyFX(getMinimumCoord(), getMaximumCoord());
    }

    @Override
    protected void onBlockAdded(IMultiblockPart newPart) {
        // NO-OP
    }

    @Override
    protected void onBlockRemoved(IMultiblockPart oldPart) {
        // NO-OP
    }

    @Override
    protected void onMachineRestored() {
        // NO-OP
    }

    @Override
    protected void onMachinePaused() {
        // NO-OP
    }

    @Override
    protected void onMachineDisassembled() {
        // NO-OP
    }

    @Override
    protected void onAssimilate(MultiblockControllerBase assimilated) {

    }

    @Override
    protected void onAssimilated(MultiblockControllerBase assimilator) {

    }

    @Override
    protected void updateClient() {

    }

    @Override
    protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        return world.isAirBlock(new BlockPos(x, y, z));
    }

    @Override
    public void readFromDisk(NBTTagCompound data) {
    }

    @Override
    public World getMachineWorld() {
        return WORLD;
    }

    @Override
    public BlockPos getMachinePos() {
        return getReferenceCoord();
    }

}
