package xyz.brassgoggledcoders.steamagerevolution.worldgen;

import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.blocks.BlockHeavyOreIndicator;

public class OreGenerator implements IWorldGenerator {

    public static ArrayList<OreEntry> oresToGenerate = Lists.newArrayList();
    public static ArrayList<String> heavyOresToGenerate = Lists.newArrayList();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
            IChunkProvider chunkProvider) {
        switch(world.provider.getDimension()) {
            case -1:
                break;
            case 0:
                generateSurface(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 1:
                break;
        }
    }

    // The actual generation method.
    private void generateSurface(World world, Random rand, int chunkX, int chunkZ) {
        for(OreEntry entry : oresToGenerate) {
            for(int k = 0; k < entry.rarity; k++) {
                int startX = chunkX + rand.nextInt(16);
                int startZ = chunkZ + rand.nextInt(16);
                int startY = rand.nextInt(entry.maxY);
                BlockPos startPos = new BlockPos(startX, startY, startZ);
                new WorldGenMinable(entry.ore, entry.veinSize).generate(world, rand, startPos);
            }
        }
        for(String entry : heavyOresToGenerate) {
            if(rand.nextInt(30) == 0) {
                int startX = chunkX + rand.nextInt(16) + 8;
                int startZ = chunkZ + rand.nextInt(16) + 8;
                int startY = rand.nextInt(20);
                BlockPos startPos = new BlockPos(startX, startY, startZ);
                Block block = Block.getBlockFromName(SteamAgeRevolution.MODID + ":" + entry + "_heavy_ore_indicator");
                if(block != null && world.getBlockState(startPos).getBlock() == Blocks.STONE) {
                    world.setBlockState(startPos, block.getDefaultState());
                    SteamAgeRevolution.instance.getLogger()
                            .devInfo("Generating " + entry + " ore tell at " + startPos.toString());
                }
            }
        }
    }

    public static void generateOreSeam(World world, BlockPos start, EnumFacing facing, int lengthM, int heightM,
            int widthM) {
        if(world.isRemote) {
            return;
        }
        if(world.isAreaLoaded(start, lengthM + widthM, false)) {
            BlockHeavyOreIndicator sourceBlock = (BlockHeavyOreIndicator) world.getBlockState(start).getBlock();
            Block block = sourceBlock.getOre();
            if(block != null) {
                for(int length = 1; length < lengthM; length++) {
                    for(int height = 0; height < heightM; height++) {
                        for(int width = 0; width < widthM; width++) {
                            if(length == 1 || length == lengthM - 1) {
                                // Cut out horizontal corners
                                if(height == heightM - 1 || height == 0) {
                                    continue;
                                }
                                // Make ends 'pointy'
                                if(width == 0 || width == widthM - 1) {
                                    continue;
                                }
                            }
                            BlockPos pos = start.up(height).offset(facing.rotateY(), width).offset(facing, length);
                            if(world.rand.nextInt(5) == 0) {
                                pos = pos.add(world.rand.nextInt(3), world.rand.nextInt(3), world.rand.nextInt(3));
                            }
                            if(new StonePredicate().apply(world.getBlockState(pos)) && pos.getY() > 0) {
                                world.setBlockState(pos, block.getDefaultState());
                            }
                        }
                    }
                }
                world.setBlockState(start, Blocks.STONE.getDefaultState());
            }
        }
    }

    public static class OreEntry {
        final String config;
        final int veinSize;
        final int rarity;
        final int maxY;
        final IBlockState ore;

        public OreEntry(String config, int rarity, int maxY, int veinSize, IBlockState ore) {
            this.config = config;
            this.rarity = rarity;
            this.maxY = maxY;
            this.veinSize = veinSize;
            this.ore = ore;
        }
    }
}