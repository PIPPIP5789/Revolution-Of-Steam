package xyz.brassgoggledcoders.steamagerevolution.pneumatic;

import com.teamacronymcoders.base.Capabilities;
import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPneumaticTube extends BlockTEBase<TileEntityPneumaticTube> {

    public static final PropertyEnum<Axis> AXIS = PropertyEnum.<Axis>create("axis", Axis.class);
    public static final AxisAlignedBB X_TUBE_AABB = new AxisAlignedBB(0.0D, 0.2D, 0.2D, 1.0D, 0.8D, 0.8D);
    public static final AxisAlignedBB Y_TUBE_AABB = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 1.0D, 0.8D);
    public static final AxisAlignedBB Z_TUBE_AABB = new AxisAlignedBB(0.2D, 0.2D, 0.0D, 0.8D, 0.8D, 1.0D);

    public BlockPneumaticTube(Material mat, String name) {
        super(mat, name);
        setDefaultState(blockState.getBaseState().withProperty(AXIS, Axis.X));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch(state.getValue(AXIS)) {
            case Y:
                return Y_TUBE_AABB;
            case Z:
                return Z_TUBE_AABB;
            default:
                return X_TUBE_AABB;
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!playerIn.getHeldItem(hand).isEmpty()
                && playerIn.getHeldItem(hand).hasCapability(Capabilities.TOOL, facing)) {
            state.cycleProperty(AXIS);
            return true;
        }

        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
            float hitZ, int meta, EntityLivingBase placer) {
        return getStateFromMeta(meta).withProperty(AXIS, facing.getOpposite().getAxis());
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        switch(meta) {
            case 0:
                return getDefaultState().withProperty(AXIS, Axis.X);
            case 1:
                return getDefaultState().withProperty(AXIS, Axis.Z);
            case 2:
                return getDefaultState().withProperty(AXIS, Axis.Y);
        }
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        Axis axis = state.getValue(AXIS);
        if(axis == Axis.Z) {
            return 1;
        }
        else if(axis == Axis.Y) {
            return 2;
        }
        else {
            return 0;
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { AXIS });
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityPneumaticTube.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityPneumaticTube();
    }
}
