package xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.tileentities.TileEntityAlloyFurnaceFluidOutput;

public class BlockAlloyFurnaceFluidOutput extends BlockMultiblockBase<TileEntityAlloyFurnaceFluidOutput> {

    public BlockAlloyFurnaceFluidOutput(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityAlloyFurnaceFluidOutput.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityAlloyFurnaceFluidOutput();
    }

}