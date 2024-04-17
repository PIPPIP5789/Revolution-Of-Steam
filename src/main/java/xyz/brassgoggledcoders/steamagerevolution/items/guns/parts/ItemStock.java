package xyz.brassgoggledcoders.steamagerevolution.items.guns.parts;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemStock extends ItemBase implements IStock {

    String name;
    float knockbackStrength;

    public ItemStock(String name, float knockbackStrength) {
        super(name);
        this.name = name;
        this.knockbackStrength = knockbackStrength;
    }

    @Override
    public String getPartName() {
        return name;
    }

    @Override
    public float getKnockbackModifier() {
        return knockbackStrength;
    }

}
