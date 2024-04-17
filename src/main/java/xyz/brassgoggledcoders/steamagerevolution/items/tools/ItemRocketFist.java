package xyz.brassgoggledcoders.steamagerevolution.items.tools;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class ItemRocketFist extends ItemBase {

    public static final int steamUsePerBlock = 10;
    public static final int capacity = 3000;

    public ItemRocketFist() {
        super("rocket_fist");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        FluidStack fluid = internal.getFluid();
        return fluid == null ? 0 : 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        doFistBoost(entityLiving, stack, timeLeft);
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

    public ActionResult<ItemStack> doFistBoost(EntityLivingBase entityIn, ItemStack stack, int timeLeft) {
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        int i = 1;
        // TODO Make more granular
        if(timeLeft == 0) {
            i = 3;
        }
        if(internal.getFluid() != null && internal.getFluid().amount >= steamUsePerBlock) {
            entityIn.motionX += (entityIn.getLookVec().normalize().x * i);
            entityIn.motionY += (entityIn.getLookVec().normalize().y * i);
            entityIn.motionZ += (entityIn.getLookVec().normalize().z * i);
            entityIn.playSound(SoundEvents.E_PARROT_IM_CREEPER, 10, 1);
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }
        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        SteamAgeRevolution.instance.getLogger().devInfo("Entity hit method called");
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if(internal.getFluid() != null && internal.getFluid().amount >= steamUsePerBlock) {
            target.world.createExplosion(null, target.posX, target.posY, target.posZ, 0.5F, false);
            internal.drain(steamUsePerBlock * 10, true);
            return true;
        }
        return false;
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
}
