package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.tileentities;

import java.util.LinkedHashMap;

import com.teamacronymcoders.base.api.IDebuggable;
import com.teamacronymcoders.base.multiblocksystem.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.ControllerBoiler;

public abstract class TileEntityBoilerPart extends MultiblockInventoryTileEntity<ControllerBoiler>
        implements IDebuggable {

    @Override
    public Class<ControllerBoiler> getMultiblockControllerType() {
        return ControllerBoiler.class;
    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new ControllerBoiler(getWorld());
    }

    @Override
    public LinkedHashMap<String, String> getDebugStrings(LinkedHashMap<String, String> debugStrings) {
        // debugStrings.put("Temperature", "" +
        // this.getMultiblockController().getInventory().getCurrentTemperature());
        debugStrings.put("Burn Time", "" + getMultiblockController().currentBurnTime);
        return debugStrings;
    }
}
