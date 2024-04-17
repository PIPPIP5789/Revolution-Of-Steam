package xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockStackHandlerWrapper;

//TODO Do from the controlller?
public class TileEntityGrinderOutput extends TileEntityGrinderPart implements ITickable {

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    @Nonnull
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                    .cast(new MultiblockStackHandlerWrapper(this, "itemOutput"));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public void update() {
        if(isConnected() && getMultiblockController().isAssembled()) {
            EnumFacing facing = getOutwardFacing();
            if(facing != null && world.getTileEntity(getPos().offset(facing)) != null) {
                TileEntity te = world.getTileEntity(getPos().offset(facing));
                if(te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())) {
                    IItemHandler other = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                            facing.getOpposite());
                    IItemHandler ours = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                    for(int slot = 0; slot < ours.getSlots(); slot++) {
                        ItemStack test = ours.getStackInSlot(slot).copy();
                        test.setCount(1);
                        if(ItemHandlerHelper.insertItem(other, test, true).isEmpty()) {
                            ItemHandlerHelper.insertItem(other, test, false);
                            ours.extractItem(slot, 1, false);
                            break;
                        }
                    }
                }
            }
        }
    }

}
