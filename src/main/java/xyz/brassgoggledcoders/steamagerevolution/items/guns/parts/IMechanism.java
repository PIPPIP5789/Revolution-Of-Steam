package xyz.brassgoggledcoders.steamagerevolution.items.guns.parts;

public interface IMechanism extends IGunPart {
    public int getReloadTime();

    public ActionType getActionType();

    public static enum ActionType {
        BOLT, SEMI, AUTO
    }

    @Override
    default GunPartType getPartType() {
        return GunPartType.MECHANISM;
    }
}
