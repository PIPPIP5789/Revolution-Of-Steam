package xyz.brassgoggledcoders.steamagerevolution.items.guns.parts;

public interface IStock extends IGunPart {
    @Override
    default GunPartType getPartType() {
        return GunPartType.STOCK;
    }

    public float getKnockbackModifier();
}
