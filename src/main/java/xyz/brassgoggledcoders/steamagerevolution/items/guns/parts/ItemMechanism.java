package xyz.brassgoggledcoders.steamagerevolution.items.guns.parts;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemMechanism extends ItemBase implements IMechanism {

    String name;
    ActionType type;

    public ItemMechanism(String name, ActionType type) {
        super(name);
        this.name = name;
        this.type = type;
        GunPartRegistry.registerPart(this);
    }

    @Override
    public ActionType getActionType() {
        return type;
    }

    @Override
    public String getPartName() {
        return name;
    }

    @Override
    public int getReloadTime() {
        return 0;
    }

}
