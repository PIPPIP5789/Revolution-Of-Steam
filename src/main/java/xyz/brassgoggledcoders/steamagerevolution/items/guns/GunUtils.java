package xyz.brassgoggledcoders.steamagerevolution.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.entities.EntityBullet;
import xyz.brassgoggledcoders.steamagerevolution.items.guns.IAmmo.AmmoType;
import xyz.brassgoggledcoders.steamagerevolution.items.guns.parts.*;
import xyz.brassgoggledcoders.steamagerevolution.items.guns.parts.IGunPart.GunPartType;

public class GunUtils {
    public static ItemStack findAmmo(EntityPlayer player, ItemStack gunStack, AmmoType type) {
        if(isValidAmmo(player.getHeldItem(EnumHand.OFF_HAND), type)) {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if(isValidAmmo(player.getHeldItem(EnumHand.MAIN_HAND), type)) {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else {
            for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if(isValidAmmo(itemstack, type)) {
                    return itemstack;
                }
            }
            return ItemStack.EMPTY;
        }
    }

    public static boolean isValidAmmoContainer(ItemStack stack, AmmoType type) {
        Item item = stack.getItem();
        if(item instanceof IAmmoContainer) {
            return ((IAmmoContainer) item).getContainedAmmo().equals(type);
        }
        return false;
    }

    public static ItemStack findAmmo(EntityPlayer player, ItemStack gunStack) {
        IChamber part = (IChamber) GunPartRegistry
                .getPart(GunUtils.getOrCreateTagCompound(gunStack).getString(GunPartType.CHAMBER.toString()));
        if(GunUtils.isValidAmmo(player.getHeldItem(EnumHand.OFF_HAND), part.getAcceptedType())) {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if(GunUtils.isValidAmmo(player.getHeldItem(EnumHand.MAIN_HAND), part.getAcceptedType())) {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else {
            for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if(GunUtils.isValidAmmo(itemstack, part.getAcceptedType())) {
                    return itemstack;
                }
            }
            return ItemStack.EMPTY;
        }
    }

    public static boolean isValidAmmo(ItemStack stack, AmmoType type) {
        Item item = stack.getItem();
        if(item instanceof IAmmo) {
            return ((IAmmo) item).getAmmoType().equals(type);
        }
        return false;
    }

    public static NBTTagCompound getOrCreateTagCompound(ItemStack stack) {
        if(!stack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setBoolean("isLoaded", false);
            stack.setTagCompound(tag);
        }

        return stack.getTagCompound();
    }

    public static void shoot(World worldIn, EntityLivingBase entityLiving, ItemStack stack) {
        EntityPlayer playerIn = (EntityPlayer) entityLiving;

        EntityBullet bullet = new EntityBullet(worldIn, playerIn);
        IBarrel part = (IBarrel) GunPartRegistry.getPart(GunUtils.getOrCreateTagCompound(stack).getString("BARREL"));
        bullet.shoot(playerIn, playerIn.getPitchYaw().x, playerIn.getRotationYawHead(), playerIn.getEyeHeight(),
                3f + part.getVelocityModifier(), part.getAccuracyModifier());
        worldIn.spawnEntity(bullet);
        IStock stock = (IStock) GunPartRegistry.getPart(GunUtils.getOrCreateTagCompound(stack).getString("STOCK"));
        playerIn.knockBack(entityLiving, 1 - stock.getKnockbackModifier(), -1, -1);
    }

    public static IGunPart getPartFromGun(ItemStack stack, GunPartType type) {
        return GunPartRegistry.getPart(getOrCreateTagCompound(stack).getString(type.toString()));
    }
}
