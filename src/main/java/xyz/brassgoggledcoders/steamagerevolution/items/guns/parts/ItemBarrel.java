package xyz.brassgoggledcoders.steamagerevolution.items.guns.parts;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemBarrel extends ItemBase implements IBarrel {

    String name;
    float velocityMod, accuracyMod;

    public ItemBarrel(String name, float velocityMod, float accuracyMod) {
        super(name);
        this.name = name;
        this.velocityMod = velocityMod;
        this.accuracyMod = accuracyMod;
        GunPartRegistry.registerPart(this);
    }

    @Override
    public String getPartName() {
        return name;
    }

    @Override
    public float getVelocityModifier() {
        return velocityMod;
    }

    @Override
    public float getAccuracyModifier() {
        return accuracyMod;
    }

}
