package xyz.brassgoggledcoders.steamagerevolution.items.tools;

import java.util.List;

import javax.annotation.*;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.IModAware;
import com.teamacronymcoders.base.client.models.IHasModel;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SARItems;

public class ItemSteamHoe extends ItemHoe implements IHasModel, IModAware {

    public static final int steamUsePerBlock = 10;
    boolean creativeTabSet = false;
    int capacity;
    String name;
    private IBaseMod mod;

    public ItemSteamHoe(String name, int capacity) {
        super(SARItems.STEAM);
        setTranslationKey(name);
        this.capacity = capacity;
        this.name = name;
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
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if(internal.getFluid() != null && internal.getFluid().amount >= steamUsePerBlock) {
            internal.drain(steamUsePerBlock, true);
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        else {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public List<String> getModelNames(List<String> modelNames) {
        modelNames.add(name);
        return modelNames;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new FluidHandlerItemStack(stack, capacity) {
            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return FluidRegistry.getFluidName(fluid).equals("steam");
            }
        };
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
    @ParametersAreNonnullByDefault
    public void getSubItems(@Nullable CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if(tab != null && tab == getCreativeTab() || tab == CreativeTabs.SEARCH) {
            subItems.addAll(getAllSubItems(Lists.newArrayList()));
        }
    }

    @Override
    public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
        itemStacks.add(new ItemStack(this, 1));
        return itemStacks;
    }

    @Override
    @Nonnull
    public Item setCreativeTab(@Nonnull CreativeTabs tab) {
        if(!creativeTabSet) {
            super.setCreativeTab(tab);
            creativeTabSet = true;
        }
        return this;
    }

    @Override
    public IBaseMod getMod() {
        return mod;
    }

    @Override
    public void setMod(IBaseMod mod) {
        this.mod = mod;
    }

    @Override
    public Item getItem() {
        return this;
    }

}
