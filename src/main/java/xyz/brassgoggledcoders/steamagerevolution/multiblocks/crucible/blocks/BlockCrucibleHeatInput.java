package xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.tileentities.TileEntityCrucibleSteamInput;

public class BlockCrucibleHeatInput extends BlockMultiblockBase<TileEntityCrucibleSteamInput> {

    public BlockCrucibleHeatInput(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityCrucibleSteamInput.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityCrucibleSteamInput();
    }
}
