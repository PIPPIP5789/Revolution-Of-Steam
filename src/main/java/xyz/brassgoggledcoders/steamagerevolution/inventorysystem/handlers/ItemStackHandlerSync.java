package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

//TODO Bring back custom stack sync packets because AFAIK vanilla only syncs when GUI is opened (?) which doesn't work for steam hammer etc.
public class ItemStackHandlerSync extends ItemStackHandlerExtractSpecific {

    protected InventoryPieceItemHandler enclosingIPiece;

    public ItemStackHandlerSync(int size) {
        super(size);
    }

    @Override
    protected void onContentsChanged(int slot) {
        enclosingIPiece.enclosingInv.enclosingMachine.markMachineDirty();
        // TODO
    }

    public void setEnclosing(InventoryPieceItemHandler inventoryPieceItemHandler) {
        enclosingIPiece = inventoryPieceItemHandler;
    }
}
