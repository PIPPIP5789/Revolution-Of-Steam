package xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.blocks;

import java.util.Optional;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.ControllerTank;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.tileentities.TileEntityTankWindow;

public class BlockTankWindow extends BlockMultiblockBase<TileEntityTankWindow> {

    public BlockTankWindow(Material material, String name) {
        super(material, name);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityTankWindow.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityTankWindow();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
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
        return worldIn.getBlockState(pos.offset(side)).getBlock() == this ? false : true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        Optional<TileEntityTankWindow> te = getTileEntity(world, pos);
        if(te.isPresent()) {
            TileEntityTankWindow window = te.get();
            ControllerTank multiblockController = window.getMultiblockController();
            if(window.isConnected() && multiblockController.isAssembled()) {
                FluidStack fluid = multiblockController.tank.getFluid();
                if(fluid != null) {
                    return fluid.getFluid().getLuminosity();
                }
            }
        }
        return state.getLightValue();
    }
}
