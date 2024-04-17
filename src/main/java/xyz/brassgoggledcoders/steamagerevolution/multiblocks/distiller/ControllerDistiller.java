package xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblocksystem.validation.ValidationError;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBuilder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.ItemStackHandlerSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;

public class ControllerDistiller extends MultiblockCraftingMachine<InventoryCraftingMachine> {

    public static final String uid = "distiller";
    public static int tankCapacity = Fluid.BUCKET_VOLUME * 8;

    public ControllerDistiller(World world) {
        super(world);
        setInventory(new InventoryBuilder<>(new InventoryCraftingMachine(this))
                .addPiece("fluidInput",
                        new InventoryPieceFluidTank(IOType.INPUT, new FluidTankSync(tankCapacity), 41, 9))
                .addPiece("itemOutput",
                        new InventoryPieceItemHandler(IOType.OUTPUT, new ItemStackHandlerSync(1), new int[] { 149 },
                                new int[] { 32 }))
                .addPiece("fluidOutput",
                        new InventoryPieceFluidTank(IOType.OUTPUT, new FluidTankSync(tankCapacity), 97, 9))
                .addSteamTank(10, 9).addPiece("progress", new InventoryPieceProgressBar(67, 32)).build());
    }

    @Override
    protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
        BlockPos first = getMinimumCoord();
        BlockPos second = new BlockPos(getMaximumCoord().getX(), getMinimumCoord().getY(), getMaximumCoord().getZ());

        // Get all blocks in bottom layer of machine & check they're radiators
        for(BlockPos pos : BlockPos.getAllInBox(first, second)) {
            if(WORLD.getBlockState(pos).getBlock() != SARObjectHolder.distiller_radiator) {
                validatorCallback.setLastError(
                        new ValidationError("steamagerevolution.multiblock.validation.distiller_radiator"));
                return false;
            }
        }
        // Same for second layer, check they're hotplates
        for(BlockPos pos : BlockPos.getAllInBox(first.up(), second.up())) {
            if(WORLD.getBlockState(pos).getBlock() != SARObjectHolder.distiller_hotplate) {
                validatorCallback.setLastError(
                        new ValidationError("steamagerevolution.multiblock.validation.distiller_hotplate"));
                return false;
            }
        }

        return super.isMachineWhole(validatorCallback);
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 34;
    }

    @Override
    public int getMinimumXSize() {
        return 3;
    }

    @Override
    public int getMinimumYSize() {
        return 5;
    }

    @Override
    public int getMinimumZSize() {
        return 3;
    }

    @Override
    public int getMaximumXSize() {
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
        return 6;
    }

    @Override
    public MultiblockMachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MultiblockMachineType(uid, SARObjectHolder.distiller_frame));
        }
        return (MultiblockMachineType) MachineType.machinesList.get(uid);
    }

}
