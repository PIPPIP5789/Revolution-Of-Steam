package xyz.brassgoggledcoders.steamagerevolution.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.tileentities.TileEntitySteamElevator;

public class BlockSteamElevator extends BlockSteamVent {

    public BlockSteamElevator(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntitySteamElevator.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntitySteamElevator();
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

    @Override
    public void action(World world, BlockPos pos) {
        TileEntitySteamElevator tile = (TileEntitySteamElevator) world.getTileEntity(pos);
        if(tile.tank.getFluidAmount() >= Fluid.BUCKET_VOLUME) {
            EnumFacing f = world.getBlockState(pos).getValue(BlockSteamVent.FACING);
            for(EntityLivingBase e : world.getEntitiesWithinAABB(EntityLivingBase.class,
                    new AxisAlignedBB(pos.offset(f)).grow(3F))) {
                e.attackEntityFrom(DamageSource.IN_FIRE, 1F);
                e.addVelocity(f.getDirectionVec().getX(), f.getDirectionVec().getY(), f.getDirectionVec().getZ());
            }
            tile.tank.drain(Fluid.BUCKET_VOLUME, true);
            SteamAgeRevolution.proxy.spawnSteamJet(pos, f);
        }
    }

}
