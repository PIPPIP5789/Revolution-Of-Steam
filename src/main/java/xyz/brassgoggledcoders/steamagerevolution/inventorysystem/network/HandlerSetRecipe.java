package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.RecipeRegistry;

public class HandlerSetRecipe implements IMessageHandler<PacketSetRecipe, IMessage> {
    @Override
    public IMessage onMessage(PacketSetRecipe message, MessageContext ctx) {
        Minecraft minecraft = Minecraft.getMinecraft();
        final WorldClient worldClient = minecraft.world;
        minecraft.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(worldClient, message);
            }
        });
        return null;
    }

    private void processMessage(WorldClient worldClient, PacketSetRecipe message) {
        TileEntity te = worldClient.getTileEntity(message.pos);
        if(te instanceof IHasInventory) {
            InventoryBasic inventory = ((IHasInventory<?>) te).getInventory();
            if(inventory instanceof InventoryCraftingMachine) {
                InventoryCraftingMachine rInventory = (InventoryCraftingMachine) inventory;
                if(message.recipeID == -1) {
                    rInventory.setCurrentRecipe(null);
                }
                else {
                    rInventory.setCurrentRecipe(RecipeRegistry.getRecipeFromNetworkID(message.recipeID));
                }
            }
        }
    }
}
