package xyz.brassgoggledcoders.steamagerevolution.worldgen;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class StonePredicate implements Predicate<IBlockState> {
    @Override
    public boolean apply(IBlockState toTest) {
        if(toTest != null) {
            Block block = toTest.getBlock();
            if(block == Blocks.BEDROCK) {
                return true;
            }
            else if(block == Blocks.STONE) {
                BlockStone.EnumType blockstone$enumtype = toTest.getValue(BlockStone.VARIANT);
                return blockstone$enumtype.isNatural();
            }
        }
        return false;
    }
}