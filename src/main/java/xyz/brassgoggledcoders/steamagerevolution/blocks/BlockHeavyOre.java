package xyz.brassgoggledcoders.steamagerevolution.blocks;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamacronymcoders.base.blocks.*;
import com.teamacronymcoders.base.client.models.generator.IHasGeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.*;
import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.base.util.files.templates.TemplateFile;
import com.teamacronymcoders.base.util.files.templates.TemplateManager;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.items.itemblocks.ItemBlockHeavyOre;

public class BlockHeavyOre extends BlockBase implements IHasGeneratedModel, IHasBlockStateMapper, IHasBlockColor {

    public static final PropertyInteger CHUNKS = PropertyInteger.create("chunks", 1, 8);
    public String type;
    public MaterialPart materialPart;

    public BlockHeavyOre(MaterialPart part, String type) {
        super(Material.ROCK, type.toLowerCase() + "_heavy_ore");
        materialPart = part;
        this.type = type;
        setDefaultState(blockState.getBaseState().withProperty(CHUNKS, 8));
        setHardness(75F);
        setItemBlock(new ItemBlockHeavyOre<>(this, "heavy_ore", materialPart.getMaterial()));
    }

    // TODO Visual depletion of ore
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(CHUNKS, meta + 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(CHUNKS).intValue() - 1;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { CHUNKS });
    }

    @Override
    public void onPlayerDestroy(World world, BlockPos pos, IBlockState state) {
        int chunks = state.getValue(BlockHeavyOre.CHUNKS).intValue();
        if(!world.isRemote && chunks > 1) {
            dropBlockAsItemWithChance(world, pos, state, 0, 0);
            world.setBlockState(pos, state.withProperty(BlockHeavyOre.CHUNKS, chunks - 1), 2);
        }
        super.onPlayerDestroy(world, pos, state);
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if(!worldIn.isRemote && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates,
                                                                  // prevents item dupe
        {
            WorldServer ws = (WorldServer) worldIn;
            LootTable table = ws.getLootTableManager()
                    .getLootTableFromLocation(new ResourceLocation(SteamAgeRevolution.MODID, "heavy_ore_" + type));
            LootContext ctx = new LootContext.Builder(ws).build();
            List<ItemStack> stacks = table.generateLootForPools(ws.rand, ctx);
            for(ItemStack stack : stacks) {
                spawnAsEntity(worldIn, pos, stack);
            }
        }
    }

    @Override
    public List<String> getModelNames(List<String> modelNames) {
        modelNames.add("materials/" + getName());
        return modelNames;
    }

    @Override
    public ResourceLocation getResourceLocation(IBlockState blockState) {
        return new ResourceLocation(SteamAgeRevolution.MODID, "materials/" + getName());
    }

    @Override
    public List<IGeneratedModel> getGeneratedModels() {
        List<IGeneratedModel> models = Lists.newArrayList();
        getResourceLocations(Lists.newArrayList()).forEach(resourceLocation -> {
            TemplateFile templateFile = TemplateManager
                    .getTemplateFile(new ResourceLocation(SteamAgeRevolution.MODID, "heavy_ore"));
            Map<String, String> replacements = Maps.newHashMap();
            replacements.put("ore",
                    new ResourceLocation(SteamAgeRevolution.instance.getID(), "blocks/heavy_ore").toString());
            templateFile.replaceContents(replacements);
            models.add(
                    new GeneratedModel("materials/" + getName(), ModelType.BLOCKSTATE, templateFile.getFileContents()));
        });

        return models;
    }

    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
        return materialPart.getColor();
    }
}
