package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntityInventoryBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCardPuncher extends TileEntityInventoryBase implements IHasGui {

    public TileEntityCardPuncher() {
        super(19);
    }

    @Override
    public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        return new GuiCardPuncher((TileEntityCardPuncher) world.getTileEntity(blockPos), entityPlayer);
    }

    @Override
    public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        return new ContainerCardPuncher((TileEntityCardPuncher) world.getTileEntity(blockPos), entityPlayer);
    }

    public void doPunch() {
        ItemStackHandler inventory = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                .cast(this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
        ItemStack cardIn = inventory.getStackInSlot(0);
        ItemStack cardOut = inventory.getStackInSlot(1);
        ItemStack dye = inventory.getStackInSlot(2);
        if(!cardIn.isEmpty() && cardOut.isEmpty()) {
            NBTTagCompound tag = new NBTTagCompound();
            if(dye.getItem() instanceof ItemDye) {
                tag.setInteger("dye", dye.getMetadata());
            }
            // TODO This is funky - in a bad way
            NonNullList<ItemStack> tempStacks = NonNullList.create();
            for(int i = 3; i < 13; i++) {
                tempStacks.add(inventory.getStackInSlot(i));
            }
            ItemStackHandler tempHandler = new ItemStackHandler(tempStacks);
            tag.setTag("inventory", tempHandler.serializeNBT());

            cardIn.setTagCompound(tag);
            inventory.insertItem(1, inventory.extractItem(0, 1, false), false);
            markDirty();
        }
    }

}
