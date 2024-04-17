package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;

public class HandlerGUITankInteract implements IMessageHandler<PacketGUITankInteract, IMessage> {

    @Override
    public IMessage onMessage(PacketGUITankInteract message, MessageContext ctx) {
        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        WorldServer worldServer = serverPlayer.getServerWorld();
        worldServer.addScheduledTask(() -> {
            BlockPos pos = message.machinePos;
            if(worldServer.isBlockLoaded(pos, false)) {
                TileEntity te = worldServer.getTileEntity(message.machinePos);
                if(te instanceof IHasInventory<?>) {
                    FluidTankSync tank = ((IHasInventory<?>) te).getInventory().getHandler(message.tankName,
                            FluidTankSync.class);
                    InventoryPlayer inventoryplayer = serverPlayer.inventory;
                    ItemStack heldStack = inventoryplayer.getItemStack();
                    // Try and empty the held container into the hovered over tank, if this fails
                    // try and fill from it
                    if(!heldStack.isEmpty()) {
                        if(inventoryplayer != null) {
                            FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(heldStack, tank,
                                    new PlayerInvWrapper(inventoryplayer), tank.getCapacity(), serverPlayer, true);
                            if(!fluidActionResult.isSuccess()) {
                                fluidActionResult = FluidUtil.tryEmptyContainerAndStow(heldStack, tank,
                                        new PlayerInvWrapper(inventoryplayer), tank.getCapacity(), serverPlayer, true);
                            }

                            if(fluidActionResult.isSuccess()) {
                                inventoryplayer.setItemStack(fluidActionResult.getResult());
                                ctx.getServerHandler()
                                        .sendPacket(new SPacketSetSlot(-1, -1, fluidActionResult.getResult()));
                                ((IHasInventory<?>) te).markMachineDirty();
                            }
                        }
                    }
                }
            }
        });
        return null;
    }

}
