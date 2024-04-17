package xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblocksystem.validation.ValidationError;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SARRecipes;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceMultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces.InventoryPieceTemperatureGauge;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.MultiFluidHandler;

public class ControllerAlloyFurnace extends MultiblockCraftingMachine<InventoryHeatable> {

    public static final String uid = "alloy_furnace";

    public ControllerAlloyFurnace(World world) {
        super(world);
        setInventory(new InventoryBuilder<>(new InventoryHeatable(this, 500))
                .addPiece("input",
                        new InventoryPieceMultiFluidTank(IOType.INPUT,
                                new MultiFluidHandler(new FluidTankSync(SARRecipes.standardMoltenCapacity),
                                        new FluidTankSync(SARRecipes.standardMoltenCapacity)),
                                new int[] { 22, 78 }, new int[] { 11, 11 })).addSteamTank(10, 10)
                .addPiece("output", new InventoryPieceFluidTank(IOType.OUTPUT,
                        new FluidTankSync(SARRecipes.standardMoltenCapacity * 2), 134, 17))
                .build());
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        // TODO
        return 1;
    }

    @Override
    protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        if(world.getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.LAVA) {
            return true;
        }
        else {
            validatorCallback
                    .setLastError(new ValidationError("steamagerevolution.multiblock.validation.alloyforgeinterior"));
            return false;
        }
    }

    @Override
    public int getMinimumXSize() {
        return 5;
    }

    @Override
    public int getMinimumZSize() {
        return 5;
    }

    @Override
    public int getMinimumYSize() {
        return 6;
    }

    @Override
    public int getMaximumXSize() {
        return 5;
    }

    @Override
    public int getMaximumZSize() {
        return 5;
    }

    @Override
    public int getMaximumYSize() {
        return 6;
    }

    @Override
    public MultiblockMachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MultiblockMachineType(uid, SARObjectHolder.alloy_furnace_frame,
                    SARObjectHolder.alloy_furnace_fluid_input, SARObjectHolder.alloy_furnace_fluid_output));
        }
        return (MultiblockMachineType) MachineType.machinesList.get(uid);
    }
}
