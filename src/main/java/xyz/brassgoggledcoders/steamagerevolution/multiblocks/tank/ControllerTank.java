package xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblocksystem.IMultiblockPart;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.SARMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.ContainerSingleTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiSingleTank;

//TODO Convert to inventory system
public class ControllerTank extends SARMultiblockBase {

    public static final String uid = "tank";
    public BlockPos minimumInteriorPos;
    public BlockPos maximumInteriorPos;
    public FluidTank tank;

    public ControllerTank(World world) {
        super(world);
        // TODO
        tank = new FluidTank(0);
    }

    @Override
    public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
        tank.readFromNBT(data);
    }

    // TODO Caching
    @Override
    protected void onMachineAssembled() {
        Pair<BlockPos, BlockPos> interiorPositions = com.teamacronymcoders.base.util.PositionUtils
                .shrinkPositionCubeBy(getMinimumCoord(), getMaximumCoord(), 1);
        minimumInteriorPos = interiorPositions.getLeft();
        maximumInteriorPos = interiorPositions.getRight();

        int blocksInside = 0;
        // TODO Expensive for loop just to increment an integer
        for(BlockPos pos : BlockPos.getAllInBox(minimumInteriorPos, maximumInteriorPos)) {
            blocksInside++;
        }
        // Size internal tank accordingly
        tank = new FluidTank(tank.getFluid(), blocksInside * Fluid.BUCKET_VOLUME * 16);
        super.onMachineAssembled();
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 26;
    }

    @Override
    public int getMaximumXSize() {
        // TODO Auto-generated method stub
        return 10;
    }

    @Override
    public int getMaximumZSize() {
        // TODO Auto-generated method stub
        return 10;
    }

    @Override
    public int getMaximumYSize() {
        // TODO Auto-generated method stub
        return 10;
    }

    @Override
    protected boolean updateServer() {
        return false;
    }

    @Override
    public void writeToDisk(NBTTagCompound data) {
        tank.writeToNBT(data);
    }

    @Override
    public MultiblockMachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MultiblockMachineType(uid));
        }
        return (MultiblockMachineType) MachineType.machinesList.get(uid);
    }

    @Override
    public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        return new GuiSingleTank(entityPlayer, tank);
    }

    @Override
    public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        return new ContainerSingleTank(entityPlayer, null);
    }
}
