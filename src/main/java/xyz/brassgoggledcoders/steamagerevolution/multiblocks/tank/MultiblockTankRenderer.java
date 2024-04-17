package xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank;

import com.teamacronymcoders.base.util.RenderingUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.tileentities.TileEntityTankCasing;

public class MultiblockTankRenderer extends TileEntitySpecialRenderer<TileEntityTankCasing> {

    protected static Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void render(TileEntityTankCasing tile, double x, double y, double z, float partialTicks, int destroyStage,
            float alpha) {
        // RE last check - ensures we only render once. Save delegate should be at
        // reference coord - lowest x,y,z etc -
        // so should always be casing.
        if(tile.isConnected() && tile.getMultiblockController().isAssembled() && tile.isMultiblockSaveDelegate()) {
            ControllerTank t = tile.getMultiblockController();
            FluidTank tank = t.tank;
            FluidStack fluid = tank.getFluid();
            double x1 = t.minimumInteriorPos.getX() - tile.getPos().getX();
            double y1 = t.minimumInteriorPos.getY() - tile.getPos().getY();
            double z1 = t.minimumInteriorPos.getZ() - tile.getPos().getZ();

            double x2 = t.maximumInteriorPos.getX() - tile.getPos().getX();
            double z2 = t.maximumInteriorPos.getZ() - tile.getPos().getZ();

            if(fluid != null) {
                BlockPos minPos = new BlockPos(x1, y1, z1);
                BlockPos maxPos = new BlockPos(x2, y1, z2);
                float d = RenderingUtils.FLUID_OFFSET;
                int yd = 1 + Math.max(0, t.maximumInteriorPos.getY() - t.minimumInteriorPos.getY());
                double height = (((float) fluid.amount / (float) tank.getCapacity())) / yd;
                RenderingUtils.renderStackedFluidCuboid(fluid, x, y, z, tile.getPos(), minPos, maxPos, d, height - d);
            }
        }
    }
}