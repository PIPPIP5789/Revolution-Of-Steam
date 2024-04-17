package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceHandler;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;

public class FluidTankSync extends FluidTank implements INBTSerializable<NBTTagCompound> {

    private InventoryPieceHandler<? extends IFluidHandler> enclosingIPiece;

    public FluidTankSync(int capacity) {
        super(capacity);
    }

    @Override
    public void onContentsChanged() {
        if(!getEnclosingIPiece().enclosingInv.enclosingMachine.getMachineWorld().isRemote) {
           // SteamAgeRevolution.instance.getLogger().devInfo("Fluid update sent");
            SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
                    new PacketFluidUpdate(getEnclosingIPiece().enclosingInv.enclosingMachine.getMachinePos(),
                            getFluid(), getEnclosingIPiece().getName()),
                    getEnclosingIPiece().enclosingInv.enclosingMachine.getMachinePos(),
                    getEnclosingIPiece().enclosingInv.enclosingMachine.getMachineWorld().provider.getDimension());
            getEnclosingIPiece().enclosingInv.enclosingMachine.markMachineDirty();
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        readFromNBT(nbt);
    }

    // Set from the InventoryPiece constructor
    public void setEnclosing(InventoryPieceHandler<? extends IFluidHandler> inventoryPieceFluidTank) {
        enclosingIPiece = inventoryPieceFluidTank;
        if(getEnclosingIPiece().enclosingInv != null
                && getEnclosingIPiece().enclosingInv.enclosingMachine instanceof TileEntity) {
            setTileEntity((TileEntity) getEnclosingIPiece().enclosingInv.enclosingMachine);
        }
    }

    public InventoryPieceHandler<? extends IFluidHandler> getEnclosingIPiece() {
        return enclosingIPiece;
    }
}
