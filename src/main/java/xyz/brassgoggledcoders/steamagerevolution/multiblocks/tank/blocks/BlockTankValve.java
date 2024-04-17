package xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.tileentities.TileEntityTankValve;

public class BlockTankValve extends BlockMultiblockBase<TileEntityTankValve> {

    public BlockTankValve(Material material, String name) {
        super(material, name);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityTankValve.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityTankValve();
    }

}
