package xyz.brassgoggledcoders.steamagerevolution.utils;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/*
 * Implementation of this interface is ~required~ to use TinkersUtils#breakExtraBlock
 */
public interface IAreaBreakingTool {
    /*
     * Implementations should elevate the method from Item to public and supercall.
     */
    public RayTraceResult rayTrace(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn);
}
