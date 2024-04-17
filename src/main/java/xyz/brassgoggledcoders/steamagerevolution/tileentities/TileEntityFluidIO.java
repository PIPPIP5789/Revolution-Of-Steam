package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.ItemStackHandlerSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;

public class TileEntityFluidIO extends TileEntityInventory<InventoryBasic> implements ITickable {

    public static final String uid = "fluid_io";
    private int fluidTransferRate = 20;

    public TileEntityFluidIO() {
        super();
        setInventory(new InventoryBuilder<>(new InventoryBasic(this))
                .addPiece("items",
                        new InventoryPieceItemHandler(new ItemStackHandlerSync(2), new int[] { 25, 134 },
                                new int[] { 33, 33 }))
                .addPiece("tank", new InventoryPieceFluidTank(new FluidTankSync(Fluid.BUCKET_VOLUME * 6), 78, 11))
                .build());
    }

    @Override
    public void update() {
        ItemStackHandlerSync stackHandler = inventory.getHandler("items", ItemStackHandlerSync.class);
        FluidTankSync fluidHandler = inventory.getHandler("tank", FluidTankSync.class);
        if(!stackHandler.getStackInSlot(0).isEmpty()) {
            IFluidHandler itemFluid = stackHandler.getStackInSlot(0)
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if(itemFluid != null && itemFluid.drain(fluidTransferRate, false) != null
                    && fluidHandler.fill(itemFluid.drain(fluidTransferRate, false), false) == fluidTransferRate) {
                fluidHandler.fill(itemFluid.drain(fluidTransferRate, true), true);
                markMachineDirty();
                sendBlockUpdate();
            }
        }
        if(!stackHandler.getStackInSlot(1).isEmpty()) {
            IFluidHandler itemFluid = stackHandler.getStackInSlot(1)
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if(itemFluid != null && fluidHandler.getFluidAmount() > 0
                    && itemFluid.fill(fluidHandler.drain(fluidTransferRate, false), false) == fluidTransferRate) {
                itemFluid.fill(fluidHandler.drain(fluidTransferRate, true), true);
                markMachineDirty();
                sendBlockUpdate();
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                || super.hasCapability(capability, facing);
    }

    @Override
    public <CAP> CAP getCapability(Capability<CAP> capability, EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                    .cast(inventory.getHandler("tank", FluidTankSync.class));
        }
        else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            // // From top expose input slot
            // if(EnumFacing.DOWN.equals(facing)) {
            // return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new
            // ItemStackHandler(NonNullList.withSize(1,
            // inventory.getHandler("items",
            // ItemStackHandlerSync.class).getStackInSlot(0))));
            // }
            // // From bottom expose output slot
            // else if(EnumFacing.UP.equals(facing)) {
            // return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new
            // ItemStackHandler(NonNullList.withSize(1,
            // inventory.getHandler("items",
            // ItemStackHandlerSync.class).getStackInSlot(1))));
            // }
            // // From sides expose both slots
            // else {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                    .cast(inventory.getHandler("items", ItemStackHandlerSync.class));
            // }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public MachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MachineType(uid));
        }
        return MachineType.machinesList.get(uid);
    }

}
