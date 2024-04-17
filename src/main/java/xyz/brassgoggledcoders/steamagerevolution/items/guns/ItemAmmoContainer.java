package xyz.brassgoggledcoders.steamagerevolution.items.guns;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.items.guns.IAmmo.AmmoType;

public class ItemAmmoContainer extends ItemBase implements IAmmoContainer {

    int max;
    AmmoType type;

    public ItemAmmoContainer(String name, int max, AmmoType type) {
        super(name);
        setMaxStackSize(1);
        this.max = max;
        this.type = type;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(getOrCreateTagCompound(stack).getInteger("count") + "/" + getMaxAmmo());
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        ItemStack ammo = GunUtils.findAmmo(playerIn, stack, type);
        if(!ammo.isEmpty()) {
            ammo.shrink(1);
            getOrCreateTagCompound(stack).setInteger("count", (getOrCreateTagCompound(stack).getInteger("count") + 1));
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public int getMaxAmmo() {
        return max;
    }

    @Override
    public int getAmmoCount(ItemStack stack) {
        return getOrCreateTagCompound(stack).getInteger("count");
    }

    @Override
    public AmmoType getContainedAmmo() {
        return type;
    }

    public static NBTTagCompound getOrCreateTagCompound(ItemStack stack) {
        if(!stack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }

        return stack.getTagCompound();
    }
}
