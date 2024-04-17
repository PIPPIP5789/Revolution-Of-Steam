package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.blocks;

import com.teamacronymcoders.base.blocks.BlockGUIBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.tileentities.TileEntityBoilerSteamOutput;

public class BlockBoilerSteamOutput extends BlockGUIBase<TileEntityBoilerSteamOutput> {

    public BlockBoilerSteamOutput(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityBoilerSteamOutput.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityBoilerSteamOutput();
    }

}
