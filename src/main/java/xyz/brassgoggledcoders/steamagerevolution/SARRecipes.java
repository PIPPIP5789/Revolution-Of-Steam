package xyz.brassgoggledcoders.steamagerevolution;

import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MachineRecipeBuilder;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities.materials.ModuleMaterials;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.ControllerAlloyFurnace;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.ControllerCrucible;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.ControllerDistiller;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace.ControllerSteamFurnace;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder.ControllerGrinder;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.ControllerSteamHammer;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks.ControllerSteelworks;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.ControllerVat;
import xyz.brassgoggledcoders.steamagerevolution.recipes.*;
import xyz.brassgoggledcoders.steamagerevolution.tileentities.TileEntityCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

@SuppressWarnings("deprecation")
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class SARRecipes {

    public static int standardMoltenCapacity = RecipeUtil.VALUE_BLOCK * 8;
    public static int standardFluidCapacity = Fluid.BUCKET_VOLUME * 16;

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().register(
                new RecipesOreToDust().setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "ore_to_dust")));
        event.getRegistry().register(new RecipesIngotToPlate()
                .setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "ingot_to_plate")));
        event.getRegistry().register(
                new RecipeAddLens().setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "add_lens")));

        for(Entry<ItemStack, ItemStack> recipe : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
            new MachineRecipeBuilder(ControllerSteamFurnace.uid).setItemInputs(recipe.getKey())
                    .setItemOutputs(recipe.getValue()).setSteamCost(1000).setCraftTime(1000).build();
        }

        for(String material : ModuleMaterials.heavyOreMaterials) {
            ItemStack oreStack = OreDictUtils.getPreferredItemStack("ore" + StringUtils.capitalize(material));
            ItemStack rockStack = OreDictUtils.getPreferredItemStack("rock" + StringUtils.capitalize(material));
            ItemStack crushedOreStack = OreDictUtils
                    .getPreferredItemStack("crushedOre" + StringUtils.capitalize(material));
            if(!crushedOreStack.isEmpty()) {
                if(!oreStack.isEmpty()) {
                    ItemStack co = crushedOreStack.copy();
                    co.setCount(3);
                    new MachineRecipeBuilder(ControllerGrinder.uid).setItemInputs(oreStack).setItemOutputs(co)
                            .setCraftTime(80).setSteamCost(Fluid.BUCKET_VOLUME).build();
                }
                if(!rockStack.isEmpty()) {
                    ItemStack co = crushedOreStack.copy();
                    co.setCount(5);
                    new MachineRecipeBuilder(ControllerGrinder.uid).setItemInputs(rockStack).setItemOutputs(co)
                            .setCraftTime(40).setSteamCost(Fluid.BUCKET_VOLUME / 2).build();
                }
            }
        }
        new MachineRecipeBuilder(ControllerGrinder.uid).setItemInputs(new ItemStack(Blocks.STONE))
                .setItemOutputs(new ItemStack(Blocks.COBBLESTONE)).setSteamCost(Fluid.BUCKET_VOLUME / 10)
                .setCraftTime(10).setSteamCost(Fluid.BUCKET_VOLUME / 10).build();
        new MachineRecipeBuilder(ControllerGrinder.uid).setItemInputs(new ItemStack(Blocks.COBBLESTONE))
                .setItemOutputs(new ItemStack(Blocks.GRAVEL)).setSteamCost(Fluid.BUCKET_VOLUME / 10)
                .setSteamCost(Fluid.BUCKET_VOLUME / 10).setCraftTime(10).build();
        new MachineRecipeBuilder(ControllerGrinder.uid).setSteamCost(Fluid.BUCKET_VOLUME / 10).setCraftTime(10)
                .setItemInputs(new ItemStack(Blocks.GRAVEL)).setItemOutputs(new ItemStack(Blocks.SAND))
                .setSteamCost(Fluid.BUCKET_VOLUME / 10).setSteamCost(Fluid.BUCKET_VOLUME / 10).setCraftTime(10).build();
        // TODO Add metal -> dust recipes to grinder!

        new MachineRecipeBuilder(ControllerAlloyFurnace.uid)
                .setFluidInputs(FluidRegistry.getFluidStack("copper", RecipeUtil.VALUE_INGOT),
                        FluidRegistry.getFluidStack("zinc", RecipeUtil.VALUE_INGOT))
                .setFluidOutputs(FluidRegistry.getFluidStack("brass", RecipeUtil.VALUE_INGOT * 2)).build();

        new MachineRecipeBuilder(ControllerSteelworks.uid)
                .setFluidInputs(FluidRegistry.getFluidStack("iron", RecipeUtil.VALUE_NUGGET))
                .setItemInputs(new ItemStack(Items.COAL, 1, 0))
                .setFluidOutputs(FluidRegistry.getFluidStack("steel", RecipeUtil.VALUE_NUGGET))
                .setSteamCost(Fluid.BUCKET_VOLUME / 600).setCraftTime(10).build();

        new MachineRecipeBuilder(ControllerSteelworks.uid)
                .setFluidInputs(FluidRegistry.getFluidStack("iron", RecipeUtil.VALUE_BLOCK))
                .setItemInputs(new ItemStack(SARObjectHolder.charcoal_block))
                .setFluidOutputs(FluidRegistry.getFluidStack("steel", RecipeUtil.VALUE_BLOCK))
                .setSteamCost(Fluid.BUCKET_VOLUME * 10).setCraftTime(6000).build();

        for(String metal : ModuleMaterials.knownMetalTypes) {

            // Known to be non-null because it is how metal types are known
            String ingot = "ingot" + metal;
            ItemStack ingotStack = OreDictUtils.getPreferredItemStack("ingot" + metal);

            String ore = "ore" + metal;
            String gear = "gear" + metal;
            String plate = "plate" + metal;
            String crushedOre = "crushedOre" + metal;
            String nugget = "nugget" + metal;
            String dust = "dust" + metal;
            String crystal = "crystal" + metal;
            String block = "block" + metal;

            ItemStack oreStack = OreDictUtils.getPreferredItemStack(ore);
            ItemStack gearStack = OreDictUtils.getPreferredItemStack(gear);
            ItemStack plateStack = OreDictUtils.getPreferredItemStack(plate);
            ItemStack crushedOreStack = OreDictUtils.getPreferredItemStack(crushedOre);
            ItemStack nuggetStack = OreDictUtils.getPreferredItemStack(nugget);
            ItemStack dustStack = OreDictUtils.getPreferredItemStack(dust);
            ItemStack crystalStack = OreDictUtils.getPreferredItemStack(crystal);
            FluidStack molten = FluidRegistry.getFluidStack(metal.toLowerCase(), RecipeUtil.VALUE_INGOT);
            FluidStack solution = FluidRegistry.getFluidStack(metal.toLowerCase() + "_solution",
                    RecipeUtil.VALUE_NUGGET * 4);

            if(molten != null) {
                FluidStack moltenCopy = molten.copy();
                moltenCopy.amount = RecipeUtil.VALUE_NUGGET;
                new MachineRecipeBuilder(ControllerCrucible.uid).setItemInputs(nugget).setFluidOutputs(moltenCopy)
                        .setSteamCost(Fluid.BUCKET_VOLUME / 32).setCraftTime(14).build();
                FluidStack moltenCopy2 = molten.copy();
                moltenCopy2.amount = RecipeUtil.VALUE_BLOCK;
                new MachineRecipeBuilder(ControllerCrucible.uid).setItemInputs(block).setFluidOutputs(moltenCopy2)
                        .setSteamCost(Fluid.BUCKET_VOLUME).setCraftTime(1200).build();
                new MachineRecipeBuilder(ControllerCrucible.uid).setItemInputs(ingot).setFluidOutputs(molten)
                        .setSteamCost(Fluid.BUCKET_VOLUME / 16).setCraftTime(120).build();
                System.out.println(molten.tag + " : " + ingotStack);
                new MachineRecipeBuilder(TileEntityCastingBench.uid).setFluidInputs(molten).setItemOutputs(ingotStack)
                        .setCraftTime(24).build();
            }
            if(!plateStack.isEmpty()) {
                ItemStack plateCopy = plateStack.copy();
                plateCopy.setCount(SARConfig.balance.plateCount);
                new MachineRecipeBuilder(ControllerSteamHammer.uid).setItemInputs(ingot).setItemOutputs(plateCopy)
                        .setSteamCost(Fluid.BUCKET_VOLUME / 4).setCraftTime(20).build();
            }
            if(!gearStack.isEmpty()) {
                // TODO
                // SteamHammerRecipe.addSteamHammerRecipe(ingot, gear, "gear");
            }
            if(!ore.isEmpty()) {
                // TODO: Use 'our' stacks not preferred
                if(FurnaceRecipes.instance().getSmeltingResult(oreStack).isEmpty()) {
                    GameRegistry.addSmelting(oreStack, ingotStack, 0.5F);
                }
            }
            if(!dust.isEmpty()) {
                // TODO: Use 'our' stacks not preferred
                if(FurnaceRecipes.instance().getSmeltingResult(dustStack).isEmpty()) {
                    GameRegistry.addSmelting(dustStack, ingotStack, 0.5F);
                }
            }
            if(!crushedOreStack.isEmpty()) {
                ItemStack nuggetCopy = nuggetStack.copy();
                nuggetCopy.setCount(3);
                if(FurnaceRecipes.instance().getSmeltingResult(nuggetStack).isEmpty()) {
                    GameRegistry.addSmelting(crushedOreStack, nuggetCopy, 0.1f);
                }
                ItemStack crushedOreCopy = crushedOreStack.copy();
                crushedOreCopy.setCount(4);

            }
            if(!crystalStack.isEmpty()) {
                if(!nugget.isEmpty() && FurnaceRecipes.instance().getSmeltingResult(crystalStack).isEmpty()) {
                    GameRegistry.addSmelting(crystalStack, nuggetStack, 0.3f);
                }
                if(solution != null) {
                    new MachineRecipeBuilder(ControllerVat.uid).setFluidOutputs(solution)
                            .setFluidInputs(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME / 4))
                            .setItemInputs(crushedOre).build();
                    new MachineRecipeBuilder(ControllerDistiller.uid).setFluidInputs(solution)
                            .setFluidOutputs(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME / 6))
                            .setItemOutputs(crystalStack).setCraftTime(20).build();
                }
            }
        }
        new MachineRecipeBuilder(ControllerVat.uid)
                .setFluidOutputs(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME))
                .setFluidInputs(FluidRegistry.getFluidStack("sulphur_dioxide", Fluid.BUCKET_VOLUME),
                        FluidRegistry.getFluidStack("water", Fluid.BUCKET_VOLUME))
                .build();

        new MachineRecipeBuilder(ControllerVat.uid)
                .setFluidOutputs(FluidRegistry.getFluidStack("liquid_glowstone", Fluid.BUCKET_VOLUME))
                .setFluidInputs(FluidRegistry.getFluidStack("lava", Fluid.BUCKET_VOLUME))
                .setItemInputs(new ItemStack(Items.REDSTONE, 4)).build();
        FumeCollectorRecipe.addRecipe(OreDictUtils.getPreferredItemStack("crystalSulphur"),
                FluidRegistry.getFluidStack("sulphur_dioxide", Fluid.BUCKET_VOLUME), 0.1f);
        new MachineRecipeBuilder(TileEntityCastingBench.uid)
                .setFluidInputs(FluidRegistry.getFluidStack("liquid_glowstone", Fluid.BUCKET_VOLUME))
                .setItemOutputs(new ItemStack(Blocks.GLOWSTONE)).setCraftTime(2400).build();
        // TODO Proper oredict support

        // TODO Tooltip/Color for fluid (when IE not present) & potion deriving name
        // from vanilla
        new MachineRecipeBuilder(ControllerVat.uid)
                .setFluidOutputs(SARBlocks.getPotionFluidStack(PotionTypes.AWKWARD.getRegistryName().getPath(),
                        RecipeUtil.VALUE_BOTTLE))
                .setFluidInputs(FluidRegistry.getFluidStack("water", RecipeUtil.VALUE_BOTTLE))
                .setItemInputs(new ItemStack(Items.NETHER_WART)).build();
        // Stolen from ImmersiveEngineering (ta Malte!)
        /*try {
            // Blame Forge for this mess. They stopped ATs from working on MixPredicate and
            // its fields by modifying them with patches
            // without providing a usable way to look up the vanilla potion recipes
            String mixPredicateName = "net.minecraft.potion.PotionHelper$MixPredicate";
            Class<?> mixPredicateClass = Class.forName(mixPredicateName);
            Field output = ReflectionHelper.findField(mixPredicateClass,
                    ObfuscationReflectionHelper.remapFieldNames(mixPredicateName, "field_185200_c"));
            Field reagent = ReflectionHelper.findField(mixPredicateClass,
                    ObfuscationReflectionHelper.remapFieldNames(mixPredicateName, "field_185199_b"));
            Field input = ReflectionHelper.findField(mixPredicateClass,
                    ObfuscationReflectionHelper.remapFieldNames(mixPredicateName, "field_185198_a"));
            output.setAccessible(true);
            reagent.setAccessible(true);
            input.setAccessible(true);
            for(Object mixPredicate : PotionHelper.POTION_TYPE_CONVERSIONS) {
                PotionType outputObj = ((IRegistryDelegate<PotionType>) output.get(mixPredicate)).get();
                new MachineRecipeBuilder("vat")
                        .setFluidOutputs(SARBlocks.getPotionFluidStack(outputObj.getRegistryName().getPath(),
                                RecipeUtil.VALUE_BOTTLE))
                        .setFluidInputs(
                                SARBlocks.getPotionFluidStack(((IRegistryDelegate<PotionType>) input.get(mixPredicate))
                                        .get().getRegistryName().getPath(), RecipeUtil.VALUE_BOTTLE))
                        .setItemInputs(((Ingredient) reagent.get(mixPredicate)).getMatchingStacks()[0]).build();
            }
        }
        catch(Exception x) {
            x.printStackTrace();
        }*/
        // End stealing

        // new VatRecipeBuilder().setFluids(FluidRegistry.getFluidStack("water",
        // Fluid.BUCKET_VOLUME))
        // .setItems(new ItemStack(plant_ash), new ItemStack(Items.COAL, 1, 1),
        // OreDictUtils.getPreferredItemStack("crystalSulphur"))
        // .setOutput(FluidRegistry.getFluidStack("liquid_explosive",
        // Fluid.BUCKET_VOLUME)).build();
        // DistillerRecipe.addRecipe(FluidRegistry.getFluidStack("liquid_explosive",
        // Fluid.BUCKET_VOLUME), null,
        // new ItemStack(Items.GUNPOWDER), 200);

        new MachineRecipeBuilder(ControllerVat.uid)
                .setFluidInputs(FluidRegistry.getFluidStack("water", Fluid.BUCKET_VOLUME))
                .setItemInputs(new ItemStack(Blocks.DIRT), new ItemStack(Items.ROTTEN_FLESH),
                        new ItemStack(Items.SUGAR))
                .setFluidOutputs(FluidRegistry.getFluidStack("slime", Fluid.BUCKET_VOLUME)).build();
        new MachineRecipeBuilder(ControllerVat.uid)
                .setFluidInputs(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME))
                .setItemInputs(new ItemStack(Items.REDSTONE))
                .setFluidOutputs(FluidRegistry.getFluidStack("lava", Fluid.BUCKET_VOLUME))
                .setItemOutputs(new ItemStack(Items.GLOWSTONE_DUST)).build();
        new MachineRecipeBuilder(ControllerDistiller.uid)
                .setFluidInputs(FluidRegistry.getFluidStack("slime", Fluid.BUCKET_VOLUME))
                .setItemOutputs(new ItemStack(Item.getItemFromBlock(Blocks.SLIME_BLOCK))).setCraftTime(20).build();
    }
}
