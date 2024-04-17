package xyz.brassgoggledcoders.steamagerevolution.items.guns;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.item.Item;

public class ItemAmmo extends ItemBase implements IAmmo {

    AmmoType type;
    int dmg;
    Item aux;

    public ItemAmmo(String name, AmmoType type, int dmg) {
        super(name);
        this.type = type;
        this.dmg = dmg;
    }

    public ItemAmmo(String name, AmmoType type, int dmg, Item aux) {
        this(name, type, dmg);
        this.aux = aux;
    }

    @Override
    public AmmoType getAmmoType() {
        return type;
    }

    @Override
    public int getBaseDamage() {
        return dmg;
    }

    @Override
    public Item getAuxAmmoItem() {
        return aux;
    }

}
