package xyz.brassgoggledcoders.steamagerevolution.items.guns.parts;

import com.teamacronymcoders.base.items.ItemBase;

import xyz.brassgoggledcoders.steamagerevolution.items.guns.IAmmo.AmmoType;

public class ItemChamber extends ItemBase implements IChamber {

    String name;
    AmmoType type;

    public ItemChamber(String name, AmmoType type) {
        super(name);
        this.name = name;
        this.type = type;
        GunPartRegistry.registerPart(this);
    }

    @Override
    public String getPartName() {
        return name;
    }

    @Override
    public AmmoType getAcceptedType() {
        return type;
    }

}
