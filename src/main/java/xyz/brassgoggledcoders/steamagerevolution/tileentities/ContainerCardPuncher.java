package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.*;
import xyz.brassgoggledcoders.steamagerevolution.items.ItemPunchcard;

public class ContainerCardPuncher extends ContainerBase {

    public ContainerCardPuncher(TileEntityCardPuncher tile, EntityPlayer entityPlayer) {
        ItemStackHandler codeInv = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                .cast(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
        // Card in
        addSlotToContainer(new SlotCard(codeInv, 0, 26, 16));
        // Card out
        addSlotToContainer(new SlotCard(codeInv, 1, 26, 53));
        // Dye
        addSlotToContainer(new SlotItemHandler(codeInv, 2, 62, 16));
        // Items
        int slotNum = 3;
        for(int horizontal = 0; horizontal < 4; horizontal++) {
            for(int vertical = 0; vertical < 4; vertical++) {

                addSlotToContainer(
                        new SlotItemHandler(codeInv, slotNum++, 98 + (horizontal * 18), 8 + (vertical * 18)));
            }
        }
        createPlayerSlots(entityPlayer.inventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    public static class SlotCard extends SlotItemHandler {
        public SlotCard(IItemHandler itemHandler, int index, int xPos, int yPos) {
            super(itemHandler, index, xPos, yPos);
        }

        @Override
        public boolean isItemValid(@Nullable ItemStack stack) {
            return stack != null && !stack.isEmpty() && stack.getItem() instanceof ItemPunchcard;
        }
    }

}
