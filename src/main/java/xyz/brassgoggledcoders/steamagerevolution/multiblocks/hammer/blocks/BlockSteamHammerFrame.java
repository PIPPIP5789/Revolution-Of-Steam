package xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.blocks;

import com.teamacronymcoders.base.multiblocksystem.rectangular.PartPosition;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.tileentities.TileEntitySteamHammerFrame;

public class BlockSteamHammerFrame extends BlockMultiblockBase<TileEntitySteamHammerFrame> {

    public static final PropertyEnum<PartPosition> position = PartPosition.createProperty("position");

    public BlockSteamHammerFrame(Material material, String name) {
        super(material, name);
        setDefaultState(blockState.getBaseState().withProperty(position, PartPosition.UNKNOWN));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, position);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(position, getTileEntity(worldIn, pos).get().getPartPosition());
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntitySteamHammerFrame.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntitySteamHammerFrame();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

}
