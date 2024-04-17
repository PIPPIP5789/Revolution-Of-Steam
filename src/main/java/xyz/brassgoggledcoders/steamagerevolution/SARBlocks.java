package xyz.brassgoggledcoders.steamagerevolution;

import java.awt.Color;

import com.teamacronymcoders.base.registrysystem.BlockRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.blocks.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.pneumatic.*;

public class SARBlocks {
    public static void registerBlocks(BlockRegistry blockRegistry) {
        registerFluids(blockRegistry);

        blockRegistry.register(new BlockCharcoal());

        blockRegistry.register(new BlockPneumaticTube(Material.IRON, "pneumatic_tube"));
        blockRegistry.register(new BlockPneumaticSender(Material.IRON, "pneumatic_sender"));
        blockRegistry.register(new BlockPneumaticRouter(Material.IRON, "pneumatic_router"));

        blockRegistry.register(new BlockBoilerCasing(Material.IRON, "boiler_casing"));
        blockRegistry.register(new BlockBoilerWaterInput(Material.IRON, "boiler_water_input"));
        blockRegistry.register(new BlockBoilerSteamOutput(Material.IRON, "boiler_steam_output"));
        blockRegistry.register(new BlockBoilerSolidFirebox(Material.IRON, "boiler_solid_firebox"));
        blockRegistry.register(new BlockBoilerLiquidFirebox(Material.IRON, "boiler_liquid_firebox"));
        blockRegistry.register(new BlockBoilerGauge(Material.IRON, "boiler_gauge"));

        blockRegistry.register(new BlockSteamVent(Material.IRON, "steam_vent"));

        blockRegistry.register(new BlockFurnaceCasing(Material.IRON, "furnace_casing"));
        blockRegistry.register(new BlockFurnaceItemInput(Material.IRON, "furnace_item_input"));
        blockRegistry.register(new BlockFurnaceItemOutput(Material.IRON, "furnace_item_output"));
        blockRegistry.register(new BlockFurnaceSteamInput(Material.IRON, "furnace_steam_input"));

        blockRegistry.register(new BlockGrinderFrame());
        blockRegistry.register(new BlockGrinderInput());
        blockRegistry.register(new BlockGrinderOutput());

        blockRegistry.register(new BlockAlloyFurnaceFrame(Material.ROCK, "alloy_furnace_frame"));
        blockRegistry.register(new BlockAlloyFurnaceFluidInput(Material.ROCK, "alloy_furnace_fluid_input"));
        blockRegistry.register(new BlockAlloyFurnaceFluidOutput(Material.ROCK, "alloy_furnace_fluid_output"));
        blockRegistry.register(new BlockAlloyFurnaceHeatInput(Material.ROCK, "alloy_furnace_steam_input"));

        blockRegistry.register(new BlockSteelworksFrame(Material.ROCK, "steelworks_frame"));
        blockRegistry.register(new BlockSteelworksIronInput(Material.ROCK, "steelworks_iron_input"));
        blockRegistry.register(new BlockSteelworksCarbonInput(Material.ROCK, "steelworks_carbon_input"));
        blockRegistry.register(new BlockSteelworksSteamInput(Material.ROCK, "steelworks_steam_input"));
        blockRegistry.register(new BlockSteelworksSteelOutput(Material.ROCK, "steelworks_steel_output"));

        blockRegistry.register(new BlockSteamHammerAnvil(Material.ANVIL, "steamhammer_anvil"));
        blockRegistry.register(new BlockSteamHammerFrame(Material.IRON, "steamhammer_frame"));
        blockRegistry.register(new BlockSteamHammerHammer(Material.IRON, "steamhammer_hammer"));
        blockRegistry.register(new BlockSteamHammerShielding(Material.IRON, "steamhammer_shielding"));

        blockRegistry.register(new BlockCastingBench(Material.ANVIL, "casting_bench"));

        blockRegistry.register(new BlockCrucibleCasing(Material.IRON, "crucible_casing"));
        blockRegistry.register(new BlockCrucibleItemInput(Material.IRON, "crucible_item_input"));
        blockRegistry.register(new BlockCrucibleHeatInput(Material.IRON, "crucible_steam_input"));
        blockRegistry.register(new BlockCrucibleFluidOutput(Material.IRON, "crucible_fluid_output"));

        blockRegistry.register(new BlockVatFrame(Material.IRON, "vat_frame"));
        blockRegistry.register(new BlockVatFluidInput(Material.IRON, "vat_fluid_input"));
        blockRegistry.register(new BlockVatOutput(Material.IRON, "vat_output"));

        blockRegistry.register(new BlockFumeCollector(Material.IRON, "fume_collector"));

        blockRegistry.register(new BlockDistillerFluidInput(Material.IRON, "distiller_fluid_input"));
        blockRegistry.register(new BlockDistillerFluidOutput(Material.IRON, "distiller_fluid_output"));
        blockRegistry.register(new BlockDistillerFrame(Material.IRON, "distiller_frame"));
        blockRegistry.register(new BlockDistillerHotplate(Material.IRON, "distiller_hotplate"));
        blockRegistry.register(new BlockDistillerRadiator(Material.IRON, "distiller_radiator"));
        blockRegistry.register(new BlockDistillerItemOutput(Material.IRON, "distiller_item_output"));

        blockRegistry.register(new BlockTrunk(Material.WOOD, "trunk"));
        blockRegistry.register(new BlockFluidIO());
        blockRegistry.register(new BlockFluidHopper(Material.IRON, "fluid_hopper"));
        blockRegistry.register(new BlockTankCasing(Material.ROCK, "tank_casing"));
        blockRegistry.register(new BlockTankValve(Material.IRON, "tank_valve"));
        blockRegistry.register(new BlockTankWindow(Material.GLASS, "tank_window"));

        //blockRegistry.register(new BlockHeater());
    }

    private static void registerFluids(BlockRegistry blockRegistry) {
        // TODO Adjust properties
        Fluid steam = new Fluid("steam", new ResourceLocation(SteamAgeRevolution.MODID, "fluids/steam"),
                new ResourceLocation(SteamAgeRevolution.MODID, "fluids/steam_flow")).setGaseous(true)
                .setTemperature(1000).setViscosity(200);

        if(!(FluidRegistry.isFluidRegistered(steam))) { // Soft registration
            FluidRegistry.registerFluid(steam);
            FluidRegistry.addBucketForFluid(steam);
        }
        blockRegistry.register(new BlockSARFluid("steam", FluidRegistry.getFluid("steam"), Material.LAVA));

        Fluid sulphur_dioxide = new Fluid("sulphur_dioxide",
                new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphur_dioxide"),
                new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphur_dioxide_flow")).setViscosity(250)
                .setGaseous(true).setDensity(-100);
        FluidRegistry.registerFluid(sulphur_dioxide);
        FluidRegistry.addBucketForFluid(sulphur_dioxide);

        blockRegistry.register(new BlockDamagingFluid("sulphur_dioxide", FluidRegistry.getFluid("sulphur_dioxide"),
                Material.WATER, SARBlocks.damageSourceGas, 2));

        Fluid sulphuric_acid = new Fluid("sulphuric_acid",
                new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphuric_acid"),
                new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphuric_acid_flow")).setViscosity(500);
        FluidRegistry.registerFluid(sulphuric_acid);
        FluidRegistry.addBucketForFluid(sulphuric_acid);

        blockRegistry.register(new BlockAcidFluid("sulphuric_acid", FluidRegistry.getFluid("sulphuric_acid"),
                Material.WATER, SARBlocks.damageSourceAcid, 4));

        // TODO TE compat?
        Fluid liquid_glowstone = new Fluid("liquid_glowstone",
                new ResourceLocation(SteamAgeRevolution.MODID, "fluids/liquid_glowstone"),
                new ResourceLocation(SteamAgeRevolution.MODID, "fluids/liquid_glowstone_flow")).setViscosity(2000)
                .setGaseous(true);
        FluidRegistry.registerFluid(liquid_glowstone);
        FluidRegistry.addBucketForFluid(liquid_glowstone);

        blockRegistry.register(new BlockLiquidGlowstone("liquid_glowstone", FluidRegistry.getFluid("liquid_glowstone"),
                Material.LAVA));

        Fluid potion = new Fluid("potion", new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution"),
                new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution_flowing")) {
            @SuppressWarnings("deprecation")
            @Override
            public String getLocalizedName(FluidStack stack) {
                return I18n.translateToLocal(
                        PotionUtils.getPotionTypeFromNBT(stack.tag).getNamePrefixed("potion.effect."));
            }

            @Override
            public int getColor(FluidStack stack) {
                return PotionUtils.getPotionColorFromEffectList(PotionUtils.getEffectsFromTag(stack.tag));
            }
        };
        FluidRegistry.registerFluid(potion);
        FluidRegistry.addBucketForFluid(potion);

        FluidRegistry
                .registerFluid(new Fluid("slime", new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution"),
                        new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution")) {
                    @Override
                    public int getColor() {
                        return Color.GREEN.getRGB();
                    }
                });
    }

    // TODO Difficulty scaling. The vanilla method results in no damage on peaceful.
    public static DamageSource damageSourceGas = new DamageSource("gas").setDamageIsAbsolute();
    public static DamageSource damageSourceAcid = new DamageSource("acid").setDamageIsAbsolute();
    public static DamageSource damageSourceBullet = new DamageSource("bullet").setProjectile();
    public static DamageSource damageSourceGrinder = new DamageSource("grinder").setDamageBypassesArmor()
            .setDamageIsAbsolute();
    public static DamageSource damageSourceHammer = new DamageSource("hammer").setDamageBypassesArmor()
            .setDamageIsAbsolute();

    public static FluidStack getPotionFluidStack(String potionType, int amount) {
        FluidStack potion = FluidRegistry.getFluidStack("potion", amount);
        potion.tag = new NBTTagCompound();
        potion.tag.setString("Potion", potionType);
        return potion;
    }
}