package xyz.brassgoggledcoders.steamagerevolution.items;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.items.tools.ItemShovelBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.IAreaBreakingTool;
import xyz.brassgoggledcoders.steamagerevolution.utils.TinkersUtils;

public class ItemEntrenchingTool extends ItemShovelBase implements IAreaBreakingTool {

    public ItemEntrenchingTool(String name, ToolMaterial materialIn) {
        super(name, materialIn);
        setHarvestLevel("shovel", materialIn.getHarvestLevel());
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        World world = player.getEntityWorld();
        for(BlockPos extra : TinkersUtils.calcAOEBlocks(stack, world, player, pos, 3, 1, 2)) {
            TinkersUtils.breakExtraBlock(stack, world, player, extra, pos);
        }
        return super.onBlockStartBreak(stack, pos, player);
    }

    @Override
    public RayTraceResult rayTrace(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn) {
        return super.rayTrace(worldIn, playerIn, false);
    }

}
