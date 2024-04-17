package xyz.brassgoggledcoders.steamagerevolution.items.tools;

import java.util.HashSet;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.items.tools.ItemToolBase;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SARItems;
import xyz.brassgoggledcoders.steamagerevolution.utils.IAreaBreakingTool;
import xyz.brassgoggledcoders.steamagerevolution.utils.TinkersUtils;

public class ItemDrill extends ItemToolBase implements IAreaBreakingTool {

    public ItemDrill(String name, ToolMaterial material) {
        super(name, material, new HashSet<>());
        setHarvestLevel("pickaxe", material.getHarvestLevel());
        setHarvestLevel("shovel", material.getHarvestLevel());
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        World world = player.getEntityWorld();

        // TODO Move this to earlier
        if(SARItems.KNOWN_ORES.contains(world.getBlockState(pos).getBlock())) {
            return true;
        }

        for(BlockPos extra : TinkersUtils.calcAOEBlocks(stack, world, player, pos, 3, 3, 1)) {
            TinkersUtils.breakExtraBlock(stack, world, player, extra, pos);
        }
        return false;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn, ItemStack stack) {
        Block block = blockIn.getBlock();
        if(block == Blocks.OBSIDIAN) {
            return toolMaterial.getHarvestLevel() == 3;
        }
        return Items.STONE_SHOVEL.canHarvestBlock(blockIn, stack);
    }

    @Override
    public RayTraceResult rayTrace(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn) {
        return super.rayTrace(worldIn, playerIn, false);
    }

}
