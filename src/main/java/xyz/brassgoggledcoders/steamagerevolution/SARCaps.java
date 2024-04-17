package xyz.brassgoggledcoders.steamagerevolution;

import com.teamacronymcoders.base.capability.NBTCapStorage;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.steamagerevolution.api.Heatable;
import xyz.brassgoggledcoders.steamagerevolution.api.IHeatable;
import xyz.brassgoggledcoders.steamagerevolution.api.fume.FurnaceCapabilityProvider;
import xyz.brassgoggledcoders.steamagerevolution.api.fume.IFumeProducer;
import xyz.brassgoggledcoders.steamagerevolution.utils.capability.NOPStorage;
import xyz.brassgoggledcoders.steamagerevolution.utils.capability.NullFactory;

@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class SARCaps {
    @CapabilityInject(IFumeProducer.class)
    public static Capability<IFumeProducer> FUME_PRODUCER;
    @CapabilityInject(IHeatable.class)
    public static Capability<IHeatable> HEATABLE;

    public static void register() {
        CapabilityManager.INSTANCE.register(IFumeProducer.class, new NOPStorage<IFumeProducer>(),
                new NullFactory<IFumeProducer>());
        CapabilityManager.INSTANCE.register(IHeatable.class, new NBTCapStorage<IHeatable>(), new Heatable.Factory());
    }

    @SubscribeEvent
    public static void attachTileCapabilities(AttachCapabilitiesEvent<TileEntity> event) {
        if(event.getObject() instanceof TileEntityFurnace) {
            event.addCapability(new ResourceLocation(SteamAgeRevolution.MODID, "fume_collector"),
                    new FurnaceCapabilityProvider((TileEntityFurnace) event.getObject()));
        }
    }
}
