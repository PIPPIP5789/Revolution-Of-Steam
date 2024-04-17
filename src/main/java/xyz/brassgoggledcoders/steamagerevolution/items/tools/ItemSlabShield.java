package xyz.brassgoggledcoders.steamagerevolution.items.tools;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSlabShield extends ItemBase {

    public ItemSlabShield() {
        super("slab_shield");
        setMaxStackSize(1);
        setMaxDamage(550);
        addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 4.0F
                        : 0.0F;
            }
        });
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BLOCK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    // @Override
    // public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World
    // worldIn, EntityPlayer playerIn,
    // EnumHand hand) {
    // playerIn.setActiveHand(hand);
    // return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    // }
}
