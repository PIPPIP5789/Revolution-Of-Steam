package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

@Deprecated
public class ContainerSingleTank extends ContainerBase {
    public ContainerSingleTank(EntityPlayer player, TileEntity tile) {
        // TODO Add fluid interaction slots
        createPlayerSlots(player.inventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}
