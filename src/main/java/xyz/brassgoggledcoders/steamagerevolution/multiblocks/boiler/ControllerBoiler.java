package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler;

import net.minecraft.init.Items;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblocksystem.IMultiblockPart;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.items.IItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSingleSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.ItemStackHandlerSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces.InventoryPieceTemperatureGauge;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.tileentities.TileEntityBoilerGauge;

//TODO Implement a Railcraft-style mechanic where if a hot boiler runs out of water and then water is re-added without cooling down first...boom.
public class ControllerBoiler extends MultiblockCraftingMachine<InventoryHeatable> {
    public static final String uid = "boiler";

    // The burn time in ticks that fuel would normally provide in the furnace is
    // divided by this amount to get the burn time said fuel provides in the boiler
    public static final int fuelDivisor = 3;
    // In mB
    public static final int fluidConversionPerTick = 5;
    public int currentBurnTime = 0;

    // Rendering-related
    public BlockPos minimumInteriorPos;
    public BlockPos maximumInteriorPos;
    public boolean hasWindow = false;

    public ControllerBoiler(World world) {
        super(world);
        setInventory(new InventoryBuilder<>(new InventoryHeatable(this, 100))
                .addPiece("waterTank",
                        new InventoryPieceFluidTank(IOType.INPUT,
                                new FluidTankSingleSync(Fluid.BUCKET_VOLUME * 16, "water"), 50, 9))
                .addPiece("steamTank",
                        new InventoryPieceFluidTank(IOType.INPUT,
                                new FluidTankSingleSync(Fluid.BUCKET_VOLUME * 4, "steam"), 142, 9))
                .addPiece("gauge", new InventoryPieceTemperatureGauge(35, 15))
                .addPiece("burnTime", new InventoryPieceBurnTime(12, 33)).build());
    }

    // TODO Client interpolation of burn time and temp
    @Override
    protected boolean updateServer() {
    	super.updateServer();
        // Temp
        SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
                new PacketSetBurnTime(getMachinePos(), currentBurnTime), getMachinePos(),
                getMachineWorld().provider.getDimension());
        // Stage 1 - Burn fuel
        if(currentBurnTime <= 0) {
            IItemHandler solidFuelHandler = getInventory().getHandler("solidFuel", ItemStackHandlerSync.class);
            IFluidHandler liquidFuelHandler = getInventory().getHandler("liquidFuel", FluidTankSingleSync.class);
            if(solidFuelHandler != null) {
	            for(int i = 0; i < solidFuelHandler.getSlots(); i++) {
	                ItemStack fuel = solidFuelHandler.getStackInSlot(i);
	                if(!fuel.isEmpty() && TileEntityFurnace.getItemBurnTime(fuel) != 0) {
	                    currentBurnTime = (int) Math.floor(TileEntityFurnace.getItemBurnTime(fuel) / fuelDivisor);
	                    fuel.shrink(1);
	                    return true;
	                }
	            }
            }
            if(liquidFuelHandler != null) {
                try {
                    FluidStack fuel = liquidFuelHandler.drain(new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME), true);
                    if(fuel == null) {}
                    else if (fuel.amount != Fluid.BUCKET_VOLUME) {
                        liquidFuelHandler.fill(fuel, true);
                    }
                    else if(fuel.getFluid().equals(FluidRegistry.LAVA)) {
                        currentBurnTime = (int) Math.floor(TileEntityFurnace.getItemBurnTime(new ItemStack(Items.LAVA_BUCKET, 1)) / (fuelDivisor));
                        return true;
                    }
                }
                catch(NullPointerException e) {}
            }
            // If we have run out of fuel to maintain temperature, rapidly cool down
            if(currentBurnTime <= 0) {
                getInventory().getCapability(SARCaps.HEATABLE, null).heat(-5);
            }
        }
        // If we're burning, we can attempt to heat
        else {
            currentBurnTime--;
            // Heat 'er up
            // At full heat, we can begin to convert water to steam
            getInventory().getCapability(SARCaps.HEATABLE, null).heat(1);
            if(getInventory().getCapability(SARCaps.HEATABLE, null).getCurrentTemperature() >= 100) {
                FluidTankSingleSync steamTank = getInventory().getHandler("steamTank", FluidTankSingleSync.class);
                FluidTankSingleSync waterTank = getInventory().getHandler("waterTank", FluidTankSingleSync.class);
                if(waterTank.getFluidAmount() >= fluidConversionPerTick) {
                    if(steamTank.getFluidAmount() <= steamTank.getCapacity() - fluidConversionPerTick) {
                        steamTank.fill(new FluidStack(FluidRegistry.getFluid("steam"), fluidConversionPerTick), true);
                        waterTank.drain(fluidConversionPerTick, true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 26;
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
    public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
        currentBurnTime = data.getInteger("burntime");
        super.onAttachedPartWithMultiblockData(part, data);
    }

    @Override
    public void writeToDisk(NBTTagCompound data) {
        data.setInteger("burntime", currentBurnTime);
        super.writeToDisk(data);
    }

    @Override
    protected void onBlockAdded(IMultiblockPart newPart) {
        if(newPart instanceof TileEntityBoilerGauge) {
            hasWindow = true;
        }
        super.onBlockAdded(newPart);
    }

    @Override
    protected void onBlockRemoved(IMultiblockPart oldPart) {
        if(oldPart instanceof TileEntityBoilerGauge && hasWindow == true) {
            hasWindow = connectedParts.stream().noneMatch(part -> part instanceof TileEntityBoilerGauge);
        }
        super.onBlockRemoved(oldPart);
    }

    @Override
    protected void onMachineAssembled() {
        Pair<BlockPos, BlockPos> interiorPositions = com.teamacronymcoders.base.util.PositionUtils
                .shrinkPositionCubeBy(getMinimumCoord(), getMaximumCoord(), 1);
        minimumInteriorPos = interiorPositions.getLeft();
        maximumInteriorPos = interiorPositions.getRight();
        super.onMachineAssembled();
    }

    @Override
    public MultiblockMachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MultiblockMachineType(uid, SARObjectHolder.boiler_frame));
        }
        return (MultiblockMachineType) MachineType.machinesList.get(uid);
    }
}
