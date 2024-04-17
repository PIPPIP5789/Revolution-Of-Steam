package xyz.brassgoggledcoders.steamagerevolution.blocks;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockFluidBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;

public class BlockIncenseFluid extends BlockFluidBase {

    public BlockIncenseFluid(String name, Fluid fluid, Material material) {
        super(name, fluid, material);
    }

    @Override
    public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state,
            @Nonnull Random rand) {
        if(world.isAirBlock(pos.up())) {
            world.setBlockState(pos.up(), SARObjectHolder.incense_spray.getDefaultState());
        }
        super.updateTick(world, pos, state, rand);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(isSourceBlock(worldIn, pos) && worldIn.getBlockState(pos.down()) != SARObjectHolder.incense_burner) {
            worldIn.setBlockToAir(pos);
        }
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor,
            float partialTicks) {
        // TODO can I have very translucent fog? Maybe i'd have to do it completely
        // custom
        return originalColor;
    }

    // 'Ghost' fluid

    @Nullable
    @Override
    public Boolean isEntityInsideMaterial(IBlockAccess world, BlockPos blockpos, IBlockState iblockstate, Entity entity,
            double yToTest, Material materialIn, boolean testingHead) {
        // TODO
        return false;
    }

    @Nullable
    @Override
    public Boolean isAABBInsideMaterial(World world, BlockPos pos, AxisAlignedBB boundingBox, Material materialIn) {
        return false;
    }

    /**
     * Called when entities are moving to check if they are inside a liquid
     *
     * @param world
     *            world that is being tested.
     * @param pos
     *            block thats being tested.
     * @param boundingBox
     *            box to test, generally the bounds of an entity that are besting
     *            tested.
     * @return null for default behavior, true if the box is within the material,
     *         false if it was not.
     */
    @Nullable
    @Override
    public Boolean isAABBInsideLiquid(World world, BlockPos pos, AxisAlignedBB boundingBox) {
        return false;
    }

    @Override
    public boolean canDrain(World world, BlockPos pos) {
        return false;
    }
}
