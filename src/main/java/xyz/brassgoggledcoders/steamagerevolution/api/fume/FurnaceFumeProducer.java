package xyz.brassgoggledcoders.steamagerevolution.api.fume;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class FurnaceFumeProducer implements IFumeProducer {

    TileEntityFurnace tile;

    public FurnaceFumeProducer(TileEntityFurnace tile) {
        this.tile = tile;
    }

    @Override
    public boolean isBurning() {
        return tile.isBurning();
    }

    @Override
    public ItemStack getCurrentFuel() {
        return tile.getStackInSlot(1);
    }

}
