package xyz.brassgoggledcoders.steamagerevolution.proxies;

import com.teamacronymcoders.base.Base;
import com.teamacronymcoders.base.util.RenderingUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.*;
import xyz.brassgoggledcoders.steamagerevolution.client.ResourceListener;
import xyz.brassgoggledcoders.steamagerevolution.entities.EntityMinecartCarrier;
import xyz.brassgoggledcoders.steamagerevolution.entities.EntityMinecartDrilling;
import xyz.brassgoggledcoders.steamagerevolution.entities.render.RenderMinecartCarrier;
import xyz.brassgoggledcoders.steamagerevolution.entities.render.RenderMinecartDrilling;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.renderers.MultiblockBoilerRenderer;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.tileentities.TileEntityBoilerCasing;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.tileentities.TileEntitySteamHammerAnvil;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.tileentities.TileEntitySteamHammerAnvilRenderer;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.MultiblockTankRenderer;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.tileentities.TileEntityTankCasing;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.MultiblockVatTankRenderer;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities.TileEntityVatFrame;
import xyz.brassgoggledcoders.steamagerevolution.tileentities.*;
import xyz.brassgoggledcoders.steamagerevolution.tileentities.renderers.*;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public ResourceListener listener;

    @Override
    public void spawnSmoke(BlockPos at) {
        World world = Minecraft.getMinecraft().world;
        if(world == null) {
            return;
        }
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, at.getX() + 0.5F, at.getY(), at.getZ() + 0.5F, 0, 0, 0);
        for(int i = 0; i < 8; i++) {
            if(world.rand.nextBoolean()) {
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, at.getX() + 0.5F, at.getY(), at.getZ() + 0.5F, 0, 0,
                        0);
            }
        }
    }

    @Override
    public void spawnSteamJet(BlockPos at, EnumFacing f) {
        World world = Minecraft.getMinecraft().world;
        if(world == null) {
            return;
        }
        for(int i = 0; i < 20; i++) {
            // TODO Random offsets
            float[] v = RenderingUtils.directionalVelocitiesOfMagnitude(f.getDirectionVec(), 1.0F);
            world.spawnParticle(EnumParticleTypes.CLOUD, at.getX() + 0.5F, at.getY() + 0.5F, at.getZ() + 0.5F, v[0],
                    v[1], v[2]);
        }
    }

    @Override
    public void spawnFX(EnumParticleTypes type, BlockPos pos) {
        World world = Minecraft.getMinecraft().world;
        if(type == EnumParticleTypes.PORTAL) {
            for(int j = 0; j < 70; ++j) {
                world.spawnParticle(type, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
                        pos.getZ() + (-0.2 + world.rand.nextDouble()), 0, 0, 0);
            }
        }
        else if(type == EnumParticleTypes.FLAME) {
            for(int j = 0; j < 5; ++j) {
                world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
                        pos.getZ() + (-0.2 + world.rand.nextDouble()), world.rand.nextGaussian(),
                        world.rand.nextGaussian(), world.rand.nextGaussian());
            }
            for(int j = 0; j < 5; ++j) {
                world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
                        pos.getZ() + (-0.2 + world.rand.nextDouble()), -world.rand.nextGaussian(),
                        -world.rand.nextGaussian(), -world.rand.nextGaussian());
            }
        }
    }

    @Override
    public void spawnMultiblockAssemblyFX(BlockPos min, BlockPos max) {
        World world = Minecraft.getMinecraft().world;
        if(world != null) {
            for(BlockPos pos : BlockPos.getAllInBox(min, max)) {
                for(int i = 0; i < 7; i++) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5F + world.rand.nextGaussian(),
                            pos.getY() + 0.5F, pos.getZ() + 0.5F + world.rand.nextGaussian(), 0, 0, 0);
                }
            }
        }
    }

    @Override
    public void registerModels() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVatFrame.class, new MultiblockVatTankRenderer());
        Base.instance.getLibProxy().registerFluidModel(FluidRegistry.getFluid("sulphur_dioxide").getBlock(),
                new ResourceLocation(SteamAgeRevolution.MODID, "sulphur_dioxide"));
        Base.instance.getLibProxy().registerFluidModel(FluidRegistry.getFluid("sulphuric_acid").getBlock(),
                new ResourceLocation(SteamAgeRevolution.MODID, "sulphuric_acid"));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCastingBench.class,
                new TileEntityCastingBenchRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySteamHammerAnvil.class,
                new TileEntitySteamHammerAnvilRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrunk.class, new TileEntityTrunkRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidIO.class, new TileEntityFluidIORenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTankCasing.class, new MultiblockTankRenderer());

        Base.instance.getLibProxy().registerFluidModel(FluidRegistry.getFluid("steam").getBlock(),
                new ResourceLocation(SteamAgeRevolution.MODID, "steam"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoilerCasing.class, new MultiblockBoilerRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidHopper.class, new TileEntityFluidHopperRenderer());
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityMinecartCarrier.class, RenderMinecartCarrier::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMinecartDrilling.class, RenderMinecartDrilling::new);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            @Override
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                if(tintIndex == 1) {
                    return SARItems.lenseTypes.get(stack.getMetadata()).getColor();
                }
                return -1;
            }
        }, SARObjectHolder.lens);
        listener = new ResourceListener();
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(listener);
    }
}
