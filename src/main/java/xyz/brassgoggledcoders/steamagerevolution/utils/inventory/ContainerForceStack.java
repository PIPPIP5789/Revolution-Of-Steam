package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;

public class ContainerForceStack extends ContainerBase {
    public ContainerForceStack(EntityPlayer player, IHasInventory<?> holder) {
        if(!holder.getInventory().getItemHandlers().isEmpty()) {
            for(InventoryPieceItemHandler iPiece : holder.getInventory()
                    .getInventoryPiecesOfType(InventoryPieceItemHandler.class)) {
                for(int slotIndex = 0; slotIndex < iPiece.getHandler().getSlots(); slotIndex++) {
                    addSlotToContainer(new SlotForceStack(iPiece.getHandler(), slotIndex,
                            iPiece.getSlotPositionX(slotIndex), iPiece.getSlotPositionY(slotIndex)));
                }
            }
        }
        createPlayerSlots(player.inventory);
        // Make sure the changes made in HandlerForceStack are sent to the client
        detectAndSendChanges();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    // TODO
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }

}
