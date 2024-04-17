package xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityVatFrame extends TileEntityVatPart {

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if(isConnected()) {
            return new AxisAlignedBB(getMultiblockController().getMinimumCoord(),
                    getMultiblockController().getMaximumCoord());
        }
        else {
            return super.getRenderBoundingBox();
        }
    }

    @Override
    public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
        return true;
    }
}
