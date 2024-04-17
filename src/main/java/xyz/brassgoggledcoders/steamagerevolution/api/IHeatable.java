package xyz.brassgoggledcoders.steamagerevolution.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHeatable extends INBTSerializable<NBTTagCompound> {

    public int getCurrentTemperature();

    public int getMaximumTemperature();

    public int getMinimumTemperature();

    void setCurrentTemperature(int temp);

    boolean heat(int by);
}
