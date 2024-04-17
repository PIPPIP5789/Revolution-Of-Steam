package xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.blocks;

import com.teamacronymcoders.base.blocks.BlockGUIBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.tileentities.TileEntityDistillerItemOutput;

public class BlockDistillerItemOutput extends BlockGUIBase<TileEntityDistillerItemOutput> {

    public BlockDistillerItemOutput(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityDistillerItemOutput.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityDistillerItemOutput();
    }
}