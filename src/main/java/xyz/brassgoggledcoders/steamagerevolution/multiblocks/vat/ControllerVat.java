package xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SARRecipes;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBuilder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.ItemStackHandlerSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.MultiFluidHandler;

public class ControllerVat extends MultiblockCraftingMachine<InventoryCraftingMachine> {

    public static final String uid = "vat";
    public static int outputCapacity = Fluid.BUCKET_VOLUME * 8;
    public static int inputCapacity = outputCapacity * 3;
    BlockPos minimumInteriorPos;
    BlockPos maximumInteriorPos;
    AxisAlignedBB bounds;

    public ControllerVat(World world) {
        super(world);
        setInventory(new InventoryBuilder<>(new InventoryCraftingMachine(this))
                .addPiece("input",
                        new InventoryPieceItemHandler(IOType.INPUT, new ItemStackHandlerSync(3),
                                new int[] { 88, 88, 88 }, new int[] { 11, 32, 53 }))
                .addPiece("inputTank",
                        new InventoryPieceMultiFluidTank(IOType.INPUT,
                                new MultiFluidHandler(new FluidTankSync(SARRecipes.standardFluidCapacity),
                                        new FluidTankSync(SARRecipes.standardFluidCapacity),
                                        new FluidTankSync(SARRecipes.standardFluidCapacity)),
                                new int[] { 12, 37, 62 }, new int[] { 9, 9, 9 }))
                .addPiece("outputTank", new InventoryPieceFluidTank(IOType.OUTPUT, outputCapacity, 143, 9)).build());
    }

    @Override
    protected void onTick() {
        for(Entity entity : WORLD.getEntitiesWithinAABB(Entity.class, bounds)) {
            if(entity instanceof EntityItem) {
                EntityItem item = (EntityItem) entity;
                ItemStackHandler handler = getInventory().getHandler("input", ItemStackHandler.class);
                if(ItemHandlerHelper.insertItem(handler, item.getItem(), true).isEmpty()) {
                    ItemHandlerHelper.insertItem(handler, item.getItem(), false);
                    item.setDead();
                }
            }
            // Simulate contact with fluid in vat when an entity falls in.
            // TODO change bounds based on fluid fill level
            FluidStack fluid = null;
            for(FluidTankSync tank : getInventory().getFluidHandlers()) {
                if(tank.getFluidAmount() > 0) {
                    fluid = tank.getFluid();
                    break;
                }
            }
            if(fluid != null && fluid.getFluid() != null && fluid.getFluid().getBlock() != null) {
                if(fluid.getFluid().getTemperature() >= FluidRegistry.LAVA.getTemperature()) {
                    entity.setFire(5);
                }
                Block fluidBlock = fluid.getFluid().getBlock();
                fluidBlock.onEntityCollision(WORLD, getReferenceCoord(), fluidBlock.getDefaultState(), entity);

            }
        }
    }

    @Override
    protected void onMachineAssembled() {
        Pair<BlockPos, BlockPos> interiorPositions = com.teamacronymcoders.base.util.PositionUtils
                .shrinkPositionCubeBy(getMinimumCoord(), getMaximumCoord(), 1);
        minimumInteriorPos = interiorPositions.getLeft();
        maximumInteriorPos = interiorPositions.getRight();
        bounds = new AxisAlignedBB(getMinimumCoord(), getMaximumCoord());
        super.onMachineAssembled();
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 40;
    }

    @Override
    public int getMinimumXSize() {
        return 4;
    }

    @Override
    public int getMinimumYSize() {
        return 3;
    }

    @Override
    public int getMinimumZSize() {
        return 4;
    }

    @Override
    public int getMaximumXSize() {
        return 10;
    }

    @Override
    public int getMaximumZSize() {
        return 10;
    }

    @Override
    public int getMaximumYSize() {
        return 10;
    }

    @Override
    protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        return WORLD.isAirBlock(new BlockPos(x, y, z));
    }

    @Override
    public MultiblockMachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MultiblockMachineType(uid, SARObjectHolder.vat_output));
        }
        return (MultiblockMachineType) MachineType.machinesList.get(uid);
    }
}
