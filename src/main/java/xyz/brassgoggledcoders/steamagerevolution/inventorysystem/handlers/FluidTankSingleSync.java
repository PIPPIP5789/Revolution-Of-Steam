package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidTankSingleSync extends FluidTankSync {

    private String fluidName;

    public FluidTankSingleSync(int capacity, String fluidName) {
        super(capacity);
        this.fluidName = fluidName;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return getFluidName().equals(FluidRegistry.getFluidName(fluid)) ? canFill() : false;
    }

    public String getFluidName() {
        return fluidName;
    }

}
