package xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockStackHandlerWrapper;

public class TileEntityCrucibleItemInput extends TileEntityCruciblePart {

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    @Nonnull
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                    .cast(new MultiblockStackHandlerWrapper(this, "itemInput"));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    public boolean isGoodForSides(IMultiblockValidator validatorCallback) {

        return true;
    }

    @Override
    public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {

        return true;
    }

    @Override
    public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {

        return false;
    }
}
