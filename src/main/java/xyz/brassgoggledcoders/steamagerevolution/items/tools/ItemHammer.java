package xyz.brassgoggledcoders.steamagerevolution.items.tools;

import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SARConfig;
import xyz.brassgoggledcoders.steamagerevolution.utils.ItemPickaxeBase;

public class ItemHammer extends ItemPickaxeBase {

    public ItemHammer() {
        super(ToolMaterial.IRON, "hammer");
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        if(player.world.isRemote || player.capabilities.isCreativeMode) {
            return false;
        }
        IBlockState state = player.world.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack oreStack = new ItemStack(Item.getItemFromBlock(block));
        ItemStack dust = OreDictUtils.getPreferredItemStack(
                "dust" + OreDictionary.getOreName(OreDictionary.getOreIDs(oreStack)[0]).substring(3));
        if(!dust.isEmpty()) {
            dust.setCount(SARConfig.balance.dustCount);
            EntityItem entityitem = new EntityItem(player.world, pos.getX(), pos.getY(), pos.getZ(), dust);
            entityitem.setDefaultPickupDelay();
            player.world.spawnEntity(entityitem);
            player.world.setBlockToAir(pos);
            itemstack.damageItem(1, player);
            return true;
        }
        return super.onBlockStartBreak(itemstack, pos, player);
    }
}
