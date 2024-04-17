package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import xyz.brassgoggledcoders.steamagerevolution.machinesystem.IMachine;

//Interface implemented by machines that hold an inventory, an instance of IMachineInventory
public interface IHasInventory<I extends InventoryBasic> extends IMachine {
    public I getInventory();

    public void setInventory(I inventory);

    public void markMachineDirty();
}
