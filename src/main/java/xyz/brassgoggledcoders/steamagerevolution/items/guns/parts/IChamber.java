package xyz.brassgoggledcoders.steamagerevolution.items.guns.parts;

import xyz.brassgoggledcoders.steamagerevolution.items.guns.IAmmo.AmmoType;

public interface IChamber extends IGunPart {
    public AmmoType getAcceptedType();

    @Override
    default GunPartType getPartType() {
        return GunPartType.CHAMBER;
    }

}
