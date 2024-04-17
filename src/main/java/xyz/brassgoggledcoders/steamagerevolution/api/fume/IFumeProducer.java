package xyz.brassgoggledcoders.steamagerevolution.api.fume;

import net.minecraft.item.ItemStack;

public interface IFumeProducer {
    boolean isBurning();

    ItemStack getCurrentFuel();
}
