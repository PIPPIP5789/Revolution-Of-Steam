package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import java.util.List;

import mezz.jei.api.gui.ITooltipCallback;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fluids.FluidStack;

public class SARFluidTooltipCallback implements ITooltipCallback<FluidStack> {
    @Override
    public void onTooltip(int slotIndex, boolean input, FluidStack ingredient, List<String> tooltip) {
        if(ingredient.tag != null && ingredient.tag.hasKey("Potion")) {
            ItemStack dummy = new ItemStack(Blocks.BEDROCK);
            dummy.setTagInfo("Potion", ingredient.tag.getTag("Potion"));
            PotionUtils.addPotionTooltip(dummy, tooltip, 1.0F);
        }
    }
}