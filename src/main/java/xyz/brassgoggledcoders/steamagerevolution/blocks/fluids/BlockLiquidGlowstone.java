package xyz.brassgoggledcoders.steamagerevolution.blocks.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockLiquidGlowstone extends BlockSARFluid {
    public BlockLiquidGlowstone(String name, Fluid fluid, Material material) {
        super(name, fluid, material);
        setLightLevel(Blocks.GLOWSTONE.getDefaultState().getLightValue());
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if(entityIn instanceof EntityLiving) {
            EntityLiving living = (EntityLiving) entityIn;
            living.addPotionEffect(
                    new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:glowing"), 20, 1, true, false));
        }
    }
}