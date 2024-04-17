package xyz.brassgoggledcoders.steamagerevolution.blocks.fluids;

import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockAcidFluid extends BlockDamagingFluid {
    public BlockAcidFluid(String name, Fluid fluid, Material material, DamageSource source, int damage) {
        super(name, fluid, material, source, damage);
    }

    @Override
    public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state,
            @Nonnull Random rand) {
        if(!world.isRemote) {
            for(EnumFacing facing : EnumFacing.VALUES) {
                if(rand.nextInt(10) == 0) {
                    BlockPos other = pos.offset(facing);
                    Material mat = world.getBlockState(other).getMaterial();
                    if(Material.GROUND.equals(mat) || Material.GRASS.equals(mat) || Material.ROCK.equals(mat)) {
                        world.setBlockToAir(other);
                    }
                }
            }
        }
        super.updateTick(world, pos, state, rand);
    }
}