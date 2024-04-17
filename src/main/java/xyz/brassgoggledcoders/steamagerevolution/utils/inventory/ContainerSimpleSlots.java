package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

@Deprecated
public class ContainerSimpleSlots extends ContainerBase {
    public ContainerSimpleSlots(EntityPlayer player, TileEntity tile, int slots) {
        for(int i = 0; i < slots; i++) {
            addSlotToContainer(new SlotItemHandler(
                    tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), i, 80 + (i * 18), 31));
        }
        createPlayerSlots(player.inventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}
