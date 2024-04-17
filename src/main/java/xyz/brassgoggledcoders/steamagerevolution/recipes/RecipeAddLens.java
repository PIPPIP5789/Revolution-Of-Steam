package xyz.brassgoggledcoders.steamagerevolution.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;

public class RecipeAddLens extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean hasGoggles = false;
        boolean hasLens = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if(!itemstack.isEmpty()) {
                Item item = itemstack.getItem();

                if(item == SARObjectHolder.goggles) {
                    hasGoggles = true;
                    continue;
                }
                else if(item == SARObjectHolder.lens) {
                    hasLens = true;
                    continue;
                }
            }
        }
        return hasGoggles && hasLens;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack goggles = null;
        ItemStack lens = null;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if(!itemstack.isEmpty()) {
                Item item = itemstack.getItem();

                if(item == SARObjectHolder.goggles) {
                    goggles = itemstack.copy();
                    continue;
                }
                else if(item == SARObjectHolder.lens) {
                    lens = itemstack.copy();
                    continue;
                }
            }
        }
        if(goggles != null && lens != null) {
            if(!goggles.hasTagCompound()) {
                goggles.setTagCompound(new NBTTagCompound());
            }
            goggles.getTagCompound().setBoolean("lens" + lens.getMetadata(), true);
            if(lens.getMetadata() == EnumDyeColor.SILVER.getMetadata()) {
                goggles.getTagCompound().setInteger("theoneprobe", 1);
            }
            return goggles;
        }
        else {
            return null;
        }
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 || height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(SARObjectHolder.goggles);
    }

}
