package xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder.tileentities.TileEntityGrinderOutput;

public class BlockGrinderOutput extends BlockMultiblockBase<TileEntityGrinderOutput> {

    public BlockGrinderOutput() {
        super(Material.IRON, "grinder_output");
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityGrinderOutput.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityGrinderOutput();
    }
}
