package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;

public class MultiFluidHandler implements IFluidHandler, INBTSerializable<NBTTagCompound> {

    public FluidTankSync[] containedTanks;

    public MultiFluidHandler(FluidTankSync... containedTanks) {
        this.containedTanks = containedTanks;
    }

    // TODO Cache me
    @Override
    public IFluidTankProperties[] getTankProperties() {
        IFluidTankProperties[] properties = new IFluidTankProperties[containedTanks.length];
        for(int i = 0; i < containedTanks.length; i++) {
            FluidTankSync tank = containedTanks[i];
            properties[i] = new FluidTankProperties(tank.getFluid(), tank.getCapacity());
        }
        return properties;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        for(FluidTankSync tank : containedTanks) {
            if(tank.fill(resource, false) == resource.amount) {
                return tank.fill(resource, doFill);
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        for(FluidTankSync tank : containedTanks) {
            FluidStack test = tank.drain(resource.amount, false);
            if(tank.getFluid().isFluidEqual(resource) && test != null && test.amount == resource.amount) {
                return tank.drain(resource.amount, doDrain);
            }
        }
        return null;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        for(FluidTankSync tank : containedTanks) {
            FluidStack test = tank.drain(maxDrain, false);
            if(test != null && test.amount == maxDrain) {
                return tank.drain(maxDrain, doDrain);
            }
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for(FluidTankSync tank : containedTanks) {
            list.appendTag(tank.serializeNBT());
        }
        tag.setTag("list", list);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        int i = 0;
        NBTTagList list = nbt.getTagList("list", 10);
        for(FluidTankSync tank : containedTanks) {
            tank.deserializeNBT(list.getCompoundTagAt(i++));
        }
    }

}
