package xyz.brassgoggledcoders.steamagerevolution.items.guns;

import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.items.guns.IAmmo.AmmoType;

public interface IAmmoContainer {
    public int getMaxAmmo();

    public int getAmmoCount(ItemStack stack);

    public AmmoType getContainedAmmo();
}
