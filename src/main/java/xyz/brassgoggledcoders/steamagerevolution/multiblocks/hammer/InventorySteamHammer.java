package xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;

//TODO Do I want to eliminate custom inventories and use callbacks? (Probably)
public class InventorySteamHammer extends InventoryCraftingMachine {

    public InventorySteamHammer(ControllerSteamHammer parent) {
        super(parent);
    }

    @Override
    public void onFinish() {
        super.onFinish();
        ((ControllerSteamHammer) enclosingMachine).onFinish();
    }

}
