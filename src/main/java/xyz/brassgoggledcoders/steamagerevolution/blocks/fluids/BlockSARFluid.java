package xyz.brassgoggledcoders.steamagerevolution.blocks.fluids;

import com.teamacronymcoders.base.blocks.BlockFluidBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class BlockSARFluid extends BlockFluidBase {
    protected String name;

    public BlockSARFluid(String name, Fluid fluid, Material material) {
        super(name, fluid, material);
        this.name = name;
    }

    // TODO This might want to be the default in BlockFluidBase?
    @Override
    public ResourceLocation getResourceLocation(IBlockState blockState) {
        return new ResourceLocation(SteamAgeRevolution.MODID, name);
    }
}
