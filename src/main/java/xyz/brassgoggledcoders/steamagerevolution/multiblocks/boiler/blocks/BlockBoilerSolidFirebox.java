package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.tileentities.TileEntityBoilerSolidFirebox;

public class BlockBoilerSolidFirebox extends BlockMultiblockBase<TileEntityBoilerSolidFirebox> {

    public BlockBoilerSolidFirebox(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityBoilerSolidFirebox.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityBoilerSolidFirebox();
    }

}
