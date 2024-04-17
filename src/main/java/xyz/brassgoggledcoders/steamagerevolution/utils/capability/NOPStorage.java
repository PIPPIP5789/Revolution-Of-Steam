package xyz.brassgoggledcoders.steamagerevolution.utils.capability;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class NOPStorage<CAP> implements Capability.IStorage<CAP> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<CAP> capability, CAP instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<CAP> capability, CAP instance, EnumFacing side, NBTBase nbt) {

    }
}
