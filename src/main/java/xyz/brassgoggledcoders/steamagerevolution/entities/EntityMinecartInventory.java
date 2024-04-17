package xyz.brassgoggledcoders.steamagerevolution.entities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.entities.EntityMinecartBase;
import com.teamacronymcoders.base.guisystem.GuiOpener;
import com.teamacronymcoders.base.guisystem.IHasGui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;

public abstract class EntityMinecartInventory<I extends InventoryBasic> extends EntityMinecartBase
        implements IHasGui, IHasInventory<I>, IInventory {

    I inventory;

    public EntityMinecartInventory(World world) {
        super(world);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));
    }

    @Override
    protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setTag("inventory", this.inventory.serializeNBT());
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    @Nonnull
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.getInventory().getItemHandlers().get(0));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        GuiOpener.openEntityGui(SteamAgeRevolution.instance, this, player, world);
        return super.processInitialInteract(player, hand);
    }

    @Override
    public World getMachineWorld() {
        return getEntityWorld();
    }

    @Override
    public BlockPos getMachinePos() {
        return getPosition();
    }

    @Override
    public I getInventory() {
        return inventory;
    }

    @Override
    public void setInventory(I inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getSizeInventory() {
        return this.getInventory().getItemHandlers().get(0).getSlots();
    }

    @Override
    public boolean isEmpty() {
        // TODO
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        ItemStack stack = this.getInventory().getItemHandlers().get(0).getStackInSlot(index);
        return stack;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        this.markMachineDirty();
        return this.getInventory().getItemHandlers().get(0).extractItem(index, count, false);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = this.getInventory().getItemHandlers().get(0).getStackInSlot(index);
        this.getInventory().getItemHandlers().get(0).setStackInSlot(index, ItemStack.EMPTY);
        this.markMachineDirty();
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.markMachineDirty();
        this.getInventory().getItemHandlers().get(0).setStackInSlot(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return this.getInventory().getItemHandlers().get(0).isItemValid(index, stack);
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
    }

    @Override
    public void markMachineDirty() {
        this.markDirty();
    }
}
