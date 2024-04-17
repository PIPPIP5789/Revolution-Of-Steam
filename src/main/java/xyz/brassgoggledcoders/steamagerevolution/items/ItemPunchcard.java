package xyz.brassgoggledcoders.steamagerevolution.items;

import java.util.List;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class ItemPunchcard extends ItemBase {

    public ItemPunchcard() {
        super("punchcard");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            ItemStackHandler items = new ItemStackHandler(16);
            items.deserializeNBT(tag.getCompoundTag("inventory"));
            tooltip.add("Color: " + EnumDyeColor.byMetadata(tag.getInteger("dye")));
            tooltip.add("Items: ");
            for(int i = 0; i < items.getSlots(); i++) {
                ItemStack item = items.getStackInSlot(i);
                if(!item.isEmpty()) {
                    tooltip.add(item.getDisplayName());
                }
            }
        }
        else {
            tooltip.add("Not programmed");
        }
    }
}
