/*
package xyz.brassgoggledcoders.steamagerevolution.compat.theoneprobe;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;

public class InventoryInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return SteamAgeRevolution.MODID + ":inventorysystem";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
            IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if(te instanceof IHasInventory) {
            IHasInventory<?> hasInventory = (IHasInventory<?>) te;
            InventoryBasic inventory = hasInventory.getInventory();
            inventory.getFluidHandlers().forEach(tank -> probeInfo.horizontal()
                    .text(tank.getEnclosingIPiece().getName()).progress(tank.getFluidAmount(), tank.getCapacity()));
            if(inventory instanceof InventoryCraftingMachine) {
                InventoryCraftingMachine recipeInventory = (InventoryCraftingMachine) inventory;
                probeInfo.horizontal().text("Progress: ").progress(recipeInventory.getCurrentTicks(),
                        recipeInventory.getCurrentRecipe().getTicksPerOperation());
            }
        }
    }
}
*/
