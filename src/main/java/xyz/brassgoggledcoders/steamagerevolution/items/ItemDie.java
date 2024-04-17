package xyz.brassgoggledcoders.steamagerevolution.items;

import java.util.Arrays;
import java.util.List;

import com.teamacronymcoders.base.items.IHasSubItems;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.item.ItemStack;

public class ItemDie extends ItemBase implements IHasSubItems {

    public static String[] dieShapes = new String[] { "Gear", "Test" };

    public ItemDie() {
        super("die");
        setMaxStackSize(1);
    }

    public static String getDieNameFromMeta(ItemStack stack) {
        return dieShapes[stack.getItemDamage()];
    }

    public static int getMetaFromDieName(String name) {
        return Arrays.asList(dieShapes).indexOf(name);
    }

    @Override
    public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
        for(int i = 0; i < dieShapes.length; i++) {
            itemStacks.add(new ItemStack(getItem(), 1, i));
        }
        return itemStacks;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return this.getTranslationKey() + "." + dieShapes[stack.getItemDamage()].toLowerCase();
    }

}
