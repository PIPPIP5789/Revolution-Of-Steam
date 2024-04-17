package xyz.brassgoggledcoders.steamagerevolution.items.guns;

import javax.annotation.Nullable;

import net.minecraft.item.Item;

public interface IAmmo {
    public AmmoType getAmmoType();

    @Nullable
    public Item getAuxAmmoItem();

    public int getBaseDamage();

    public enum AmmoType {
        BALL, CARTRIDGE, JACKETED
    }
}
