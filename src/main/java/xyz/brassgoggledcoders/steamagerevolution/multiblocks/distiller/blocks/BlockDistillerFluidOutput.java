package xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.tileentities.TileEntityDistillerFluidOutput;

public class BlockDistillerFluidOutput extends BlockMultiblockBase<TileEntityDistillerFluidOutput> {

    public BlockDistillerFluidOutput(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityDistillerFluidOutput.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityDistillerFluidOutput();
    }
}