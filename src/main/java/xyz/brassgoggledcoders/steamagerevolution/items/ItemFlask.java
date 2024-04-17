package xyz.brassgoggledcoders.steamagerevolution.items;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.IGeneratedModel;
import com.teamacronymcoders.base.items.IHasSubItems;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

public class ItemFlask extends ItemBase implements IHasSubItems {

    private int capacity;

    public ItemFlask(String name, int capacity) {
        super(name);
        setMaxStackSize(1);
        setHasSubtypes(true);
        this.capacity = capacity;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer) entityLiving : null;

        if(entityplayer != null) {
            if(!worldIn.isRemote) {
                FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                        .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                if(internal.getFluid() != null && internal.getFluid().amount >= RecipeUtil.VALUE_BOTTLE) {
                    for(PotionEffect potioneffect : PotionUtils.getEffectsFromTag(internal.getFluid().tag)) {
                        if(potioneffect.getPotion().isInstant()) {
                            potioneffect.getPotion().affectEntity(entityplayer, entityplayer, entityLiving,
                                    potioneffect.getAmplifier(), 1.0D);
                        }
                        else {
                            entityLiving.addPotionEffect(new PotionEffect(potioneffect));
                        }
                    }
                    if(!entityplayer.capabilities.isCreativeMode) {
                        internal.drain(RecipeUtil.VALUE_BOTTLE, true);
                    }
                }
            }
        }
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        return internal.getFluid() != null ? 32 / 3 : 0;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
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
            if(fluid.tag != null && fluid.tag.hasKey("Potion")) {
                ItemStack dummy = new ItemStack(Blocks.BEDROCK);
                dummy.setTagInfo("Potion", fluid.tag.getTag("Potion"));
                PotionUtils.addPotionTooltip(dummy, tooltip, 1.0F);
            }
            tooltip.add(fluid.amount + "mB/" + capacity + "mB");
        }
    }

    @Override
    public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
        itemStacks.add(new ItemStack(this));
        return itemStacks;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new FluidHandlerItemStack(stack, capacity) {
            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return fluid.getFluid() == FluidRegistry.getFluid("potion");
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
    public List<IGeneratedModel> getGeneratedModels() {
        return Lists.newArrayList();
    }
}
