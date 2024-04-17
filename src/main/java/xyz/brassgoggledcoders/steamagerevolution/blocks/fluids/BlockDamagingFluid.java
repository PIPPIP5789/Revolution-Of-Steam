package xyz.brassgoggledcoders.steamagerevolution.blocks.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockDamagingFluid extends BlockSARFluid {

    DamageSource source;
    int damage;

    public BlockDamagingFluid(String name, Fluid fluid, Material material, DamageSource source, int damage) {
        super(name, fluid, material);
        this.source = source;
        this.damage = damage;
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        entityIn.attackEntityFrom(source, damage);
    }
}
