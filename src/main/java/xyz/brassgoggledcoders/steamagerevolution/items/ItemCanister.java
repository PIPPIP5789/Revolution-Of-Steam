package xyz.brassgoggledcoders.steamagerevolution.items;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.IGeneratedModel;
import com.teamacronymcoders.base.items.IHasSubItems;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;

public class ItemCanister extends ItemBase implements IHasSubItems {

    private int capacity;

    public ItemCanister(String name, int capacity) {
        super(name);
        setMaxStackSize(1);
        setHasSubtypes(true);
        this.capacity = capacity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        FluidStack fluid = internal.getFluid();
        if(fluid == null) {
            tooltip.add("0mB/" + capacity + "mB");
        }
        else {
            tooltip.add(fluid.getLocalizedName());
            tooltip.add(fluid.amount + "mB/" + capacity + "mB");
        }
    }

    @Override
    public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
        itemStacks.add(new ItemStack(SARObjectHolder.canister));
        for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            ItemStack filledStack = new ItemStack(SARObjectHolder.canister);
            filledStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
                    .fill(new FluidStack(fluid, capacity), true);
            itemStacks.add(filledStack);
        }
        return itemStacks;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new FluidHandlerItemStack(stack, capacity);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        return 1.0D - ((double) internal.getFluid().amount / capacity);

    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        return internal.getFluid() != null;
    }

    @Override
    public List<IGeneratedModel> getGeneratedModels() {
        return Lists.newArrayList();
    }
}
