package xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.tileentities.TileEntityAlloyFurnaceHeatInput;

public class BlockAlloyFurnaceHeatInput extends BlockMultiblockBase<TileEntityAlloyFurnaceHeatInput> {

    public BlockAlloyFurnaceHeatInput(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<TileEntityAlloyFurnaceHeatInput> getTileEntityClass() {
        return TileEntityAlloyFurnaceHeatInput.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityAlloyFurnaceHeatInput();
    }

}