package xyz.brassgoggledcoders.steamagerevolution.items.guns;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.items.guns.parts.*;
import xyz.brassgoggledcoders.steamagerevolution.items.guns.parts.IGunPart.GunPartType;
import xyz.brassgoggledcoders.steamagerevolution.items.guns.parts.IMechanism.ActionType;

public class ItemGun extends ItemBase {

    @ObjectHolder(value = SteamAgeRevolution.MODID + ":bullet")
    public static final Item bullet = null;

    public ItemGun() {
        super("gun");
    }

    @Override
    @SideOnly(Side.CLIENT)
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(GunUtils.getOrCreateTagCompound(stack).getString("BARREL"));
        tooltip.add(GunUtils.getOrCreateTagCompound(stack).getString("MECHANISM"));
        tooltip.add(GunUtils.getOrCreateTagCompound(stack).getString("CHAMBER"));
        tooltip.add(GunUtils.getOrCreateTagCompound(stack).getString("STOCK"));
        if(GunUtils.getOrCreateTagCompound(stack).getBoolean("isLoaded")) {
            tooltip.add("Loaded");
        }
        else {
            tooltip.add("Unloaded");
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        GunUtils.getOrCreateTagCompound(stack);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if(!GunUtils.getOrCreateTagCompound(stack).getBoolean("isLoaded")) {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        }
        else {
            for(int i = 0; i < GunPartType.values().length; i++) { // TODO Don't hardcode number of parts
                IGunPart part = GunUtils.getPartFromGun(stack, GunPartType.values()[i]);
                if(part != null) {
                    part.onItemRightClick(worldIn, playerIn, handIn);
                }
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {
        for(int i = 0; i < GunPartType.values().length; i++) { // TODO Don't hardcode number of parts
            IGunPart part = GunUtils.getPartFromGun(stack, GunPartType.values()[i]);
            if(part != null) {
                part.onUsingTick(stack, entityLiving, count);
            }
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        int i = getMaxItemUseDuration(stack) - timeLeft;
        if(i < 60) {
            ItemStack ammo = GunUtils.findAmmo((EntityPlayer) entityLiving, stack); // FIXME
            if(!ammo.isEmpty()) {
                GunUtils.getOrCreateTagCompound(stack).setTag("loaded", ammo.writeToNBT(new NBTTagCompound()));
                ammo.shrink(1);
                GunUtils.getOrCreateTagCompound(stack).setBoolean("isLoaded", true);
            }
            return;
        }

    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        if(!GunUtils.getOrCreateTagCompound(stack).getBoolean("isLoaded") || ActionType.AUTO.equals(
                ((IMechanism) GunPartRegistry.getPart(GunUtils.getOrCreateTagCompound(stack).getString("MECHANISM")))
                        .getActionType())) {
            return 72000;
        }
        return super.getMaxItemUseDuration(stack);
    }

}
