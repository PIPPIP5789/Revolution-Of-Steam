package xyz.brassgoggledcoders.steamagerevolution.recipes;

import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SARConfig;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities.materials.ModuleMaterials;

@ObjectHolder(SteamAgeRevolution.MODID)
public class RecipesIngotToPlate extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe>
        implements IRecipe {

    public static final Item hammer = null;

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean hasHammer = false;
        boolean hasIngot = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if(!itemstack.isEmpty()) {
                Item item = itemstack.getItem();

                if(item == hammer) {
                    hasHammer = true;
                    continue;
                }
                // TODO
                for(String metal : ModuleMaterials.knownMetalTypes) {
                    if(OreDictionary.containsMatch(false, OreDictionary.getOres("ingot" + metal, false), itemstack)) {
                        hasIngot = true;
                    }
                }
            }
        }
        return hasHammer && hasIngot;
    }

    // TODO
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if(!itemstack.isEmpty()) {
                for(int id : OreDictionary.getOreIDs(itemstack)) {
                    String name = OreDictionary.getOreName(id);
                    if(name.contains("ingot")) {
                        ItemStack plate = OreDictUtils.getPreferredItemStack("plate" + name.substring(5));
                        plate.setCount(SARConfig.balance.plateCount);
                        return plate;
                    }
                }
            }

        }
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 || height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        final NonNullList<ItemStack> remainingItems = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for(int i = 0; i < remainingItems.size(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);

            if(!itemstack.isEmpty() && itemstack.getItem() == hammer) {
                remainingItems.set(i, damageItem(itemstack.copy()));
            }
            else {
                remainingItems.set(i, ForgeHooks.getContainerItem(itemstack));
            }
        }

        return remainingItems;
    }

    private ItemStack damageItem(final ItemStack stack) {
        final EntityPlayer craftingPlayer = ForgeHooks.getCraftingPlayer();
        if(stack.attemptDamageItem(1, craftingPlayer.getRNG(),
                craftingPlayer instanceof EntityPlayerMP ? (EntityPlayerMP) craftingPlayer : null)) {
            ForgeEventFactory.onPlayerDestroyItem(craftingPlayer, stack, null);
            return ItemStack.EMPTY;
        }

        return stack;
    }

}
