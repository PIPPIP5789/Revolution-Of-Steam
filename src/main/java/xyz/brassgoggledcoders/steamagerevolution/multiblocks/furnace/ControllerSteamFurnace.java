package xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace;

import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBuilder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.ItemStackHandlerSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;

public class ControllerSteamFurnace extends MultiblockCraftingMachine<InventoryCraftingMachine> {

    public static final String uid = "steam_furnace";

    public ControllerSteamFurnace(World world) {
        super(world);
        setInventory(new InventoryBuilder<>(new InventoryCraftingMachine(this))
                .addPiece("input",
                        new InventoryPieceItemHandler(IOType.INPUT, new ItemStackHandlerSync(1), new int[] { 48 },
                                new int[] { 33 }))
                .addPiece("output",
                        new InventoryPieceItemHandler(IOType.OUTPUT, new ItemStackHandlerSync(1), new int[] { 108 },
                                new int[] { 33 }))
                .addSteamTank(13, 9).addPiece("progress", new InventoryPieceProgressBar(72, 33)).build());
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 26;
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
        return 6;
    }

    @Override
    public int getMaximumZSize() {
        return 6;
    }

    @Override
    public int getMaximumYSize() {
        return 6;
    }

    @Override
    public MultiblockMachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MultiblockMachineType(uid, SARObjectHolder.furnace_casing));
        }
        return (MultiblockMachineType) MachineType.machinesList.get(uid);
    }
}
