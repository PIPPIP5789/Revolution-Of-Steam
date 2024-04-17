package xyz.brassgoggledcoders.steamagerevolution.blocks;

import com.teamacronymcoders.base.blocks.BlockGUIBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.tileentities.TileEntityCardPuncher;

public class BlockCardPuncher extends BlockGUIBase<TileEntityCardPuncher> {

    public BlockCardPuncher(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        // TODO Auto-generated method stub
        return TileEntityCardPuncher.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        // TODO Auto-generated method stub
        return new TileEntityCardPuncher();
    }
}
