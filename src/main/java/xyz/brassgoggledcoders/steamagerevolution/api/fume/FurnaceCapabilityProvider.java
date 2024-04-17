package xyz.brassgoggledcoders.steamagerevolution.api.fume;

import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import xyz.brassgoggledcoders.steamagerevolution.SARCaps;

public class FurnaceCapabilityProvider implements ICapabilityProvider {

    TileEntityFurnace tile;

    public FurnaceCapabilityProvider(TileEntityFurnace tile) {
        this.tile = tile;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == SARCaps.FUME_PRODUCER;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(hasCapability(capability, facing)) {
            return SARCaps.FUME_PRODUCER.cast(new FurnaceFumeProducer(tile));
        }
        return null;
    }

}
