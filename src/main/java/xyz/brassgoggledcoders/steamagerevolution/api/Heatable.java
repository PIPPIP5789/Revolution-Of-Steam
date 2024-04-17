package xyz.brassgoggledcoders.steamagerevolution.api;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTTagCompound;

public class Heatable implements IHeatable {

    int maxTemp;
    int temperature;

    public Heatable() {
        this(100);
    }

    public Heatable(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("temp", temperature);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        temperature = nbt.getInteger("temperature");
    }

    @Override
    public int getCurrentTemperature() {
        return temperature;
    }

    @Override
    public int getMaximumTemperature() {
        return 100;
    }

    @Override
    public int getMinimumTemperature() {
        return 0;
    }

    @Override
    public void setCurrentTemperature(int currentTemperature) {
        temperature = currentTemperature;
        if(currentTemperature < getMinimumTemperature()) {
            temperature = getMinimumTemperature();
        }
    }

    // Returns true if at max heat
    @Override
    public boolean heat(int by) {
        temperature += by;
        if(temperature >= maxTemp) {
            temperature = maxTemp;
            return true;
        }
        else if(temperature < 0) {
            temperature = 0;
        }
        return false;
    }

    public static class Factory implements Callable<IHeatable> {
        @Override
        public IHeatable call() throws Exception {
            return new Heatable();
        }
    }

}
