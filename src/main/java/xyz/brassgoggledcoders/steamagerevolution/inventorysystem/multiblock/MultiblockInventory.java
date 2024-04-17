package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock;

import com.teamacronymcoders.base.multiblocksystem.IMultiblockPart;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.ContainerInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIInventory;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.SARMultiblockBase;

public abstract class MultiblockInventory<INV extends InventoryBasic> extends SARMultiblockBase
        implements IHasInventory<INV> {
    protected INV inventory;

    protected MultiblockInventory(World world) {
        super(world);
    }

    @Override
    protected void onBlockAdded(IMultiblockPart newPart) {
        if(newPart instanceof MultiblockInventoryTileEntity) {
            MultiblockInventoryTileEntity<?> te = (MultiblockInventoryTileEntity<?>) newPart;
            //SteamAgeRevolution.instance.getLogger().devInfo(te.getBlockType().getLocalizedName());
            if(te.getAssociatedInventoryPiece() != null) {
            	SteamAgeRevolution.instance.getLogger().devInfo(te.getAssociatedInventoryPiece().toString());
                te.getInventory().addNewPiece(te.getAssociatedInventoryPiece());
            }
        }
    }

    @Override
    public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
        inventory.deserializeNBT(data.getCompoundTag("inventory"));
    }

    @Override
    public void writeToDisk(NBTTagCompound data) {
        data.setTag("inventory", inventory.serializeNBT());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        return new GUIInventory(entityPlayer, this);
    }

    @Override
    public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        return new ContainerInventory(entityPlayer, this);
    }

    @Override
    public INV getInventory() {
        return inventory;
    }

    @Override
    public void setInventory(INV inventory) {
        this.inventory = inventory;
    }

    @Override
    public void markMachineDirty() {
        markReferenceCoordDirty();
    }
}
