package xyz.brassgoggledcoders.steamagerevolution.items.itemblocks;

import com.teamacronymcoders.base.client.models.IHasModel;
import com.teamacronymcoders.base.items.itemblocks.ItemBlockModel;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemBlockCharcoal<T extends Block & IHasModel> extends ItemBlockModel<T> {

    public ItemBlockCharcoal(T block) {
        super(block);
    }

    @Override
    public int getItemBurnTime(ItemStack stack) {
        return TileEntityFurnace.getItemBurnTime(new ItemStack(Items.COAL)) * 9;
    }

}
