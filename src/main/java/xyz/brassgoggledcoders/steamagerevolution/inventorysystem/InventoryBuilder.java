package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSingleSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces.InventoryPieceProgressBar;

public class InventoryBuilder<INV extends InventoryBasic> {

    private INV amBuilding;

    public InventoryBuilder(INV inventory) {
        this.amBuilding = inventory;
    }

    // FIXME Adding type arguments causes errors for whatever reason
    public <PIECE extends InventoryPiece> InventoryBuilder<INV> addPiece(String name, PIECE piece) {
        amBuilding.inventoryPieces.put(name, piece);
        piece.setName(name);
        piece.setParent(amBuilding);
        return this;
    }

    public InventoryBuilder<INV> addSteamTank(int x, int y) {
        this.addPiece("steamTank", new InventoryPieceFluidTank(IOType.POWER,
                new FluidTankSingleSync(Fluid.BUCKET_VOLUME * 16, "steam"), x, y));
        return this;
    }

    public INV build() {
        amBuilding.createSublists();
        return amBuilding;
    }

    public InventoryBuilder<INV> setProgressBar(int x, int y) {
        this.addPiece("progress", new InventoryPieceProgressBar(x, y));
        return this;
    }

}
