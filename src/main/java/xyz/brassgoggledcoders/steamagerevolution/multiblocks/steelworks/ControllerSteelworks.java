package xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBuilder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.ItemStackHandlerSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;

public class ControllerSteelworks extends MultiblockCraftingMachine<InventoryCraftingMachine> {

    public static final String uid = "steelworks";
    public static final int IRON_STEEL_CONVERSION = 11664;

    public ControllerSteelworks(World world) {
        super(world);
        setInventory(new InventoryBuilder<>(new InventoryCraftingMachine(this))
                .addPiece("itemInput", new InventoryPieceItemHandler(IOType.INPUT, 83, 31))
                .addPiece("fluidInput", new InventoryPieceFluidTank(IOType.INPUT, new FluidTankSync(25000), 41, 24))
                .addPiece("steelOutput", new InventoryPieceFluidTank(IOType.OUTPUT, new FluidTankSync(10000), 147, 24))
                .addSteamTank(10, 24).setProgressBar(110, 31).build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean updateServer() {
        super.updateServer();

        IItemHandler carbonHandler = getInventory().getHandler("itemInput", ItemStackHandlerSync.class);
        if(carbonHandler != null) {
            for (int i = 0; i < carbonHandler.getSlots(); i++) {
                ItemStack fuel = carbonHandler.getStackInSlot(i);
                if(!fuel.isEmpty() && fuel.getItem().equals(Items.COAL)) {
                    System.out.println(inventory);
                    System.out.println(inventory.inventoryPieces);
                    System.out.println(inventory.inventoryPieces.get("fluidInput"));
                    FluidTankSync ironHandler = ((InventoryPieceHandler<FluidTankSync>) inventory.inventoryPieces.get("fluidInput")).getHandler();

                    if(ironHandler.getFluid() != null && ironHandler.getFluid().amount >= IRON_STEEL_CONVERSION) {
                        //ironHandler.drain(IRON_STEEL_CONVERSION, true);
                        //carbonHandler.extractItem(i, 1, false);
                    }
                }
            }
        } // Test
        return false;
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 97;
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
        return 9;
    }

    @Override
    public MultiblockMachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MultiblockMachineType(uid, SARObjectHolder.steelworks_frame));
        }
        return (MultiblockMachineType) MachineType.machinesList.get(uid);
    }

    @Override
    public int getMaximumXSize() {
        return getMinimumXSize();
    }

    @Override
    public int getMaximumZSize() {
        return getMinimumZSize();
    }

    @Override
    public int getMaximumYSize() {
        return getMinimumYSize();
    }
}
