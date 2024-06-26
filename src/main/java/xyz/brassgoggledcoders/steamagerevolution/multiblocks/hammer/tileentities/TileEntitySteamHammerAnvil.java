package xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockStackHandlerWrapper;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.ContainerSimpleSlots;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiSimpleSlots;

public class TileEntitySteamHammerAnvil extends TileEntitySteamHammerPart implements IHasGui {
    @Override
    public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
        return true;
    }

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

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if(isConnected()) {
            return new AxisAlignedBB(getMultiblockController().getMinimumCoord(),
                    getMultiblockController().getMaximumCoord());
        }
        else {
            return super.getRenderBoundingBox();
        }
    }

    @Override
    public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        return new GuiSimpleSlots(entityPlayer, this, 2);
    }

    @Override
    public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        // TODO Auto-generated method stub
        return new ContainerSimpleSlots(entityPlayer, this, 2);
    }
}
