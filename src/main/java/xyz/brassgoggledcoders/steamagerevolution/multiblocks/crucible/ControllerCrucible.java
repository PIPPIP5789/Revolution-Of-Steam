package xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.ItemStackHandlerSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces.InventoryPieceTemperatureGauge;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

public class ControllerCrucible extends MultiblockCraftingMachine<InventoryHeatable> {

    public static final String uid = "crucible";
    BlockPos minimumInteriorPos;
    BlockPos maximumInteriorPos;

    public ControllerCrucible(World world) {
        super(world);
        setInventory(new InventoryBuilder<>(new InventoryHeatable(this, 1000))
                .addPiece("itemInput",
                        new InventoryPieceItemHandler(IOType.INPUT, new ItemStackHandlerSync(1), new int[] { 53 },
                                new int[] { 34 }))
                .addPiece("fluidOutput",
                        new InventoryPieceFluidTank(IOType.OUTPUT, new FluidTankSync(RecipeUtil.VALUE_BLOCK * 4), 108,
                                19))
                .addSteamTank(10, 19)
                .addPiece("progress", new InventoryPieceProgressBar(76, 33)).build());
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 17;
    }

    @Override
    public int getMinimumXSize() {
        return 3;
    }

    @Override
    public int getMinimumZSize() {
        return 3;
    }

    @Override
    public int getMinimumYSize() {
        return 3;
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
        return 5;
    }

    @Override
    public MultiblockMachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MultiblockMachineType(uid, SARObjectHolder.crucible_casing));
        }
        return (MultiblockMachineType) MachineType.machinesList.get(uid);
    }
}
