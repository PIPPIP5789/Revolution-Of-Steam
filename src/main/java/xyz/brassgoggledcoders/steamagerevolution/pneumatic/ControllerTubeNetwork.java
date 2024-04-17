package xyz.brassgoggledcoders.steamagerevolution.pneumatic;

import com.teamacronymcoders.base.multiblocksystem.IMultiblockPart;
import com.teamacronymcoders.base.multiblocksystem.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.*;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.ItemStackHandlerSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.IMultiblockMachine;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;

//TODO Implement a stripped down version of the multiblock API specifically meant for tubes
//TODO Do some magic to make adjacent tube networks merge under one controller
public class ControllerTubeNetwork extends MultiblockControllerBase
        implements IMultiblockMachine, IHasInventory<InventoryBasic> {

    public static int maxDistance = 16;
    private IMultiblockPart input = null;
    private IMultiblockPart output = null;
    private InventoryBasic inventory;
    // TODO private boolean hasCache = false;
    public static int rate = 5;
    public static final String uid = "pneumatic_tube_network";

    public ControllerTubeNetwork(World world) {
        super(world);
        setInventory(new InventoryBuilder<>(new InventoryBasic(this))
                .addPiece("buffer", new InventoryPieceItemHandler(null, 0, 0)).build());
    }

    @Override
    public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
        getInventory().deserializeNBT(data.getCompoundTag("inventory"));
    }

    @Override
    protected void onBlockAdded(IMultiblockPart newPart) {
        if(input == null && newPart instanceof TileEntityPneumaticSender) {
            input = newPart;
        }
        else if(output == null && newPart instanceof TileEntityPneumaticRouter) {
            output = newPart;
        }
    }

    @Override
    protected void onBlockRemoved(IMultiblockPart oldPart) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onMachineAssembled() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onMachineRestored() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onMachinePaused() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onMachineDisassembled() {
        // TODO Auto-generated method stub

    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 3;
    }

    @Override
    public int getMaximumXSize() {
        return maxDistance;
    }

    @Override
    public int getMaximumZSize() {
        return maxDistance;
    }

    @Override
    public int getMaximumYSize() {
        return maxDistance;
    }

    @Override
    public int getMinimumXSize() {
        return 1;
    }

    @Override
    public int getMinimumYSize() {
        return 1;
    }

    @Override
    public int getMinimumZSize() {
        return 1;
    }

    @Override
    protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
        // TODO Check there is a valid route with correctly rotated pipes here
        return input != null && output != null;
    }

    @Override
    protected void onAssimilate(MultiblockControllerBase assimilated) {
    }

    @Override
    protected void onAssimilated(MultiblockControllerBase assimilator) {
    }

    // TODO Run logic every second, not every tick
    @Override
    protected boolean updateServer() {
        boolean hasChanged = false;
        IItemHandler buffer = getInventory().getHandler("buffer", ItemStackHandlerSync.class);
        // Search for item handlers adjacent to our input
        IItemHandler[] sends = new IItemHandler[EnumFacing.VALUES.length];
        for(EnumFacing facing : EnumFacing.VALUES) {
            BlockPos test = input.getWorldPosition().offset(facing);
            if(WORLD.getTileEntity(test) != null
                    && WORLD.getTileEntity(test).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
                sends[facing.getIndex()] = WORLD.getTileEntity(test)
                        .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
            }
        }
        // Try absorb items into buffer
        for(IItemHandler sendInventory : sends) {
            if(sendInventory != null) {
                for(int i = 0; i < sendInventory.getSlots(); i++) {
                    if(ItemHandlerHelper
                            .insertItem(buffer, sendInventory.getStackInSlot(i).copy().splitStack(rate), true)
                            .isEmpty()) {
                        // getWorld().playSound(null, getPos(), SoundEvents.ENTITY_CAT_HISS,
                        // SoundCategory.BLOCKS, 1, 1);
                        ItemHandlerHelper.insertItem(buffer, sendInventory.getStackInSlot(i).splitStack(rate), false);
                        hasChanged = true;
                        break;
                    }
                }
            }
        }
        // Try output from buffer
        EnumFacing facing = WORLD.getBlockState(output.getWorldPosition()).getValue(BlockPneumaticRouter.FACING);
        TileEntity outputToTE = WORLD.getTileEntity(output.getWorldPosition().offset(facing));
        if(outputToTE != null && outputToTE.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
            IItemHandler outputTo = outputToTE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
            if(ItemHandlerHelper.insertItem(outputTo, buffer.getStackInSlot(0).copy().splitStack(rate), true)
                    .isEmpty()) {
                ItemHandlerHelper.insertItem(outputTo, buffer.getStackInSlot(0).splitStack(rate), false);
                return true;
            }
        }
        return hasChanged;
    }

    @Override
    protected void updateClient() {

    }

    @Override
    protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    public void writeToDisk(NBTTagCompound data) {
        data.setTag("inventory", getInventory().serializeNBT());
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

    @Override
    public InventoryBasic getInventory() {
        return inventory;
    }

    @Override
    public void setInventory(InventoryBasic inventory) {
        this.inventory = inventory;
    }

    @Override
    public void markMachineDirty() {
        markReferenceCoordDirty();
    }

    @Override
    public MultiblockMachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MultiblockMachineType(uid, SARObjectHolder.pneumatic_sender));
        }
        return (MultiblockMachineType) MachineType.machinesList.get(uid);
    }
}
