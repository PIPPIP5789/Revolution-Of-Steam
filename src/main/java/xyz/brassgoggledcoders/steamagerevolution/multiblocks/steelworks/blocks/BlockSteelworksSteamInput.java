package xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks.tileentities.TileEntitySteelworksSteamInput;

public class BlockSteelworksSteamInput extends BlockMultiblockBase<TileEntitySteelworksSteamInput> {

    public BlockSteelworksSteamInput(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntitySteelworksSteamInput.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntitySteelworksSteamInput();
    }

}