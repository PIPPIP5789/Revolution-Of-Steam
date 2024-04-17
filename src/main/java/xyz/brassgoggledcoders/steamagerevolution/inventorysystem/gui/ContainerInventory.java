package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;

public class ContainerInventory extends ContainerBase {
    public ContainerInventory(EntityPlayer player, IHasInventory<? extends InventoryBasic> inventoryHolder) {
        if(!inventoryHolder.getInventory().getItemHandlers().isEmpty()) {
            for(InventoryPieceItemHandler iPiece : inventoryHolder.getInventory()
                    .getInventoryPiecesOfType(InventoryPieceItemHandler.class)) {
                for(int slotIndex = 0; slotIndex < iPiece.getHandler().getSlots(); slotIndex++) {
                    addSlotToContainer(new SlotItemHandler(iPiece.getHandler(), slotIndex,
                            iPiece.getSlotPositionX(slotIndex), iPiece.getSlotPositionY(slotIndex)));
                }
            }
        }
        createPlayerSlots(player.inventory);
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
