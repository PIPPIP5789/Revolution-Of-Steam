package xyz.brassgoggledcoders.steamagerevolution.tileentities.renderers;

import com.teamacronymcoders.base.util.RenderingUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.tileentities.TileEntityCastingBench;

public class TileEntityCastingBenchRenderer extends TileEntitySpecialRenderer<TileEntityCastingBench> {

    protected static Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void render(TileEntityCastingBench tile, double x, double y, double z, float partialTicks, int destroyStage,
            float alpha) {
        FluidTank tank = tile.getInventory().getHandler("tank", FluidTankSync.class);
        FluidStack liquid = tank.getFluid();

        if(liquid != null) {

            float height = ((float) liquid.amount) / (float) tank.getCapacity();

            float d = RenderingUtils.FLUID_OFFSET;
            RenderingUtils.renderFluidCuboid(liquid, tile.getPos(), x, y, z, d, d + 0.2F, d, 1d - d, height - d,
                    1d - d);
        }
    }
}