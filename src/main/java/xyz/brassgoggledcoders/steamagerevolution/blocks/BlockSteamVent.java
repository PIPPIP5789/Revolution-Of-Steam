package xyz.brassgoggledcoders.steamagerevolution.blocks;

import com.teamacronymcoders.base.Capabilities;
import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.tileentities.TileEntitySteamVent;

public class BlockSteamVent extends BlockTEBase<TileEntitySteamVent> {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockSteamVent(Material material, String name) {
        super(material, name);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.byIndex(meta);
        return getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntitySteamVent.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntitySteamVent();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!playerIn.getHeldItem(hand).isEmpty()
                && playerIn.getHeldItem(hand).hasCapability(Capabilities.TOOL, facing)) {
            worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycleProperty(FACING));
            return true;

        }
        return false;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if(!worldIn.isRemote) {
            if(worldIn.isBlockPowered(pos)) {
                action(worldIn, pos);
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(!worldIn.isRemote) {
            if(worldIn.isBlockPowered(pos)) {
                action(worldIn, pos);
            }
        }
    }

    public void action(World world, BlockPos pos) {
        TileEntitySteamVent tile = getTileEntity(world, pos).get();
        if(tile.tank.getFluidAmount() >= Fluid.BUCKET_VOLUME) {
            EnumFacing f = world.getBlockState(pos).getValue(BlockSteamVent.FACING);
            for(EntityLivingBase e : world.getEntitiesWithinAABB(EntityLivingBase.class,
                    new AxisAlignedBB(pos.offset(f)).grow(3F))) {
                e.attackEntityFrom(DamageSource.IN_FIRE, 3F);
            }
            tile.tank.drain(Fluid.BUCKET_VOLUME, true);
            SteamAgeRevolution.proxy.spawnSteamJet(pos, f);
        }
    }
}
