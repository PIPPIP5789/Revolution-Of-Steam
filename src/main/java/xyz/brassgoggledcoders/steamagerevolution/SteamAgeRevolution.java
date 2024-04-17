package xyz.brassgoggledcoders.steamagerevolution;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.mojang.authlib.GameProfile;
import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.registrysystem.*;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.entities.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities.materials.ModuleMaterials;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.HandlerSetBurnTime;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.PacketSetBurnTime;
import xyz.brassgoggledcoders.steamagerevolution.network.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.LootFunctionOredict;
import xyz.brassgoggledcoders.steamagerevolution.utils.StackComparator;

@Mod(modid = SteamAgeRevolution.MODID, name = SteamAgeRevolution.MODNAME, version = SteamAgeRevolution.MODVERSION, dependencies = SteamAgeRevolution.DEPENDENCIES)
@EventBusSubscriber
public class SteamAgeRevolution extends BaseModFoundation<SteamAgeRevolution> {

    public static final String MODID = "steamagerevolution";
    public static final String MODNAME = "Steam Age Revolution";
    public static final String MODVERSION = "@VERSION@";
    // TODO Versioned BASE Dependency
    public static final String DEPENDENCIES = "required-after:base;before:guideapi@[1.12-2.1.4-60,)";
    @Instance("steamagerevolution")
    public static SteamAgeRevolution instance;
    public static CreativeTabs tab = new SARTab();
    @SidedProxy(clientSide = "xyz.brassgoggledcoders.steamagerevolution.proxies.ClientProxy", serverSide = "xyz.brassgoggledcoders.steamagerevolution.proxies.CommonProxy")
    public static xyz.brassgoggledcoders.steamagerevolution.proxies.CommonProxy proxy;

    static {
        FluidRegistry.enableUniversalBucket(); // Note 3
    }

    public static String profileName = "[" + MODNAME + "]";
    public static GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(profileName.getBytes()), profileName);

    public SteamAgeRevolution() {
        super(MODID, MODNAME, MODVERSION, tab, true);
    }

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityEntry> entityRegistryEvent) {
        EntityRegistry entityRegistry = SteamAgeRevolution.instance.getRegistry(EntityRegistry.class, "ENTITY");
        entityRegistry.register(EntityDeadInventor.class);
        net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(EntityDeadInventor.class, 10, 1, 2,
                EnumCreatureType.MONSTER, Biomes.DESERT, Biomes.ROOFED_FOREST, Biomes.JUNGLE, Biomes.EXTREME_HILLS);// TODO
        entityRegistry.register(EntityMinecartCarrier.class);
        entityRegistry.register(EntityMinecartDrilling.class);
    }

    @Override
    public void registerItems(ItemRegistry itemRegistry) {
        SARItems.registerItems(itemRegistry);
    }

    @Override
    public void registerBlocks(BlockRegistry blockRegistry) {
        SARBlocks.registerBlocks(blockRegistry);
    }

    @Override
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        proxy.preInit(event);
        getLibProxy().addSidedBlockDomain();
        proxy.registerModels();
        // Reminder - Side is the side you're sending to (recieving side)
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerFluidUpdate.class, PacketFluidUpdate.class,
                Side.CLIENT);
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerIncreaseHunger.class,
                PacketIncreaseHunger.class, Side.SERVER);
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerSetRecipe.class, PacketSetRecipe.class,
                Side.CLIENT);
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerGUITankInteract.class,
                PacketGUITankInteract.class, Side.SERVER);
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerSetTemperature.class,
                PacketSetTemperature.class, Side.CLIENT);
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerSetBurnTime.class, PacketSetBurnTime.class,
                Side.CLIENT);
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerSetTemperature.class, PacketSetTemperature.class, Side.CLIENT);
        SARCaps.register();
        ModuleMaterials.knownMetalTypes.add("Iron");
        ModuleMaterials.knownMetalTypes.add("Gold");

        for(String material : ModuleMaterials.heavyOreMaterials) {
            LootTableList.register(new ResourceLocation(SteamAgeRevolution.MODID, "heavy_ore_" + material));
        }
        LootFunctionManager.registerFunction(new LootFunctionOredict.Serializer());

    }

    @Override
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
        OreDictionary.registerOre("blockCharcoal", SARObjectHolder.charcoal_block);
        OreDictionary.registerOre("dustCharcoal", SARObjectHolder.charcoal_powder);
        OreDictionary.registerOre("listAllmeatraw", Items.CHICKEN);
        OreDictionary.registerOre("listAllmeatraw", Items.BEEF);
        OreDictionary.registerOre("listAllmeatraw", Items.PORKCHOP);
        OreDictionary.registerOre("listAllmeatraw", Items.MUTTON);
        OreDictionary.registerOre("listAllmeatraw", Items.RABBIT);
        proxy.init(event);
    }

    @Override
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        proxy.postInit(event);
    }

    @Override
    public SteamAgeRevolution getInstance() {
        return instance;
    }

    public static class SARTab extends CreativeTabs {

        public SARTab() {
            super(MODID);
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(SARObjectHolder.hammer);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> items) {
            super.displayAllRelevantItems(items);
            items.sort(new StackComparator());
        }
    }

    @Override
    public boolean hasConfig() {
        return false;
    }
}
