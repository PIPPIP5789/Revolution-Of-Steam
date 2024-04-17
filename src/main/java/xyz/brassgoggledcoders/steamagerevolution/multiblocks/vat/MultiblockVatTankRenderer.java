package xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat;

import com.teamacronymcoders.base.util.RenderingUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities.TileEntityVatFrame;

public class MultiblockVatTankRenderer extends TileEntitySpecialRenderer<TileEntityVatFrame> {

    protected static Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void render(TileEntityVatFrame tile, double x, double y, double z, float partialTicks, int destroyStage,
            float alpha) {
        // RE last check - ensures we only render once. Save delegate should be at
        // reference coord - lowest x,y,z etc -
        // so should always be casing.
        if(tile.isConnected() && tile.getMultiblockController().isAssembled() && tile.isMultiblockSaveDelegate()) {
            ControllerVat ctrlr = tile.getMultiblockController();
            FluidTank outputTank = ctrlr.getInventory().getHandler("outputTank", FluidTankSync.class);
            FluidStack fluid = null;
            if(outputTank.getFluid() != null) {
                fluid = outputTank.getFluid();
            }
            else {
                for(FluidTankSync tank : ctrlr.getInventory().getTypedFluidHandlers(IOType.INPUT)) {
                    if(tank.getFluid() != null) {
                        fluid = tank.getFluid();
                    }
                }
            }

            if(fluid != null) {
                double x1 = ctrlr.minimumInteriorPos.getX() - tile.getPos().getX();
                double y1 = ctrlr.minimumInteriorPos.getY() - tile.getPos().getY();
                double z1 = ctrlr.minimumInteriorPos.getZ() - tile.getPos().getZ();

                double x2 = ctrlr.maximumInteriorPos.getX() - tile.getPos().getX();
                double z2 = ctrlr.maximumInteriorPos.getZ() - tile.getPos().getZ();
                BlockPos minPos = new BlockPos(x1, y1, z1);
                BlockPos maxPos = new BlockPos(x2, y1, z2);
                float d = RenderingUtils.FLUID_OFFSET;
                int yd = 1 + Math.max(0, ctrlr.maximumInteriorPos.getY() - ctrlr.minimumInteriorPos.getY());
                double height = (((float) fluid.amount / (float) outputTank.getCapacity())) / yd;
                RenderingUtils.renderStackedFluidCuboid(fluid, x, y, z, ctrlr.minimumInteriorPos, minPos, maxPos, d,
                        height - d);
            }
        }
    }
}