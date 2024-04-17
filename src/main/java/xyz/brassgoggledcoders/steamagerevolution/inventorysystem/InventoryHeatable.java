package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.steamagerevolution.SARCaps;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.Heatable;
import xyz.brassgoggledcoders.steamagerevolution.api.IHeatable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network.PacketSetTemperature;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;

public class InventoryHeatable extends InventoryCraftingMachine {
    public IHeatable internal;

    public InventoryHeatable(IHasInventory<? extends InventoryHeatable> parent, int maxTemperature) {
        super(parent);
        internal = new Heatable(maxTemperature);
    }

    @Override
    public boolean updateServer() {
    	//FMLLog.warning("" + getCapability(SARCaps.HEATABLE, null).getCurrentTemperature());
        SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
                new PacketSetTemperature(enclosingMachine.getMachinePos(),
                        getCapability(SARCaps.HEATABLE, null).getCurrentTemperature()),
                enclosingMachine.getMachinePos(), enclosingMachine.getMachineWorld().provider.getDimension());
        return super.updateServer();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = super.serializeNBT();
        tag.setTag("heat", internal.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        internal.deserializeNBT(tag.getCompoundTag("heat"));
        super.deserializeNBT(tag);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == SARCaps.HEATABLE;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return SARCaps.HEATABLE.cast(internal);
    }

}
