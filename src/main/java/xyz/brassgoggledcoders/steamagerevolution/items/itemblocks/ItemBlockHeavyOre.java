package xyz.brassgoggledcoders.steamagerevolution.items.itemblocks;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.blocks.IHasBlockColor;
import com.teamacronymcoders.base.client.models.IHasModel;
import com.teamacronymcoders.base.items.IHasItemColor;
import com.teamacronymcoders.base.items.itemblocks.ItemBlockModel;
import com.teamacronymcoders.base.materialsystem.materials.Material;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class ItemBlockHeavyOre<B extends Block & IHasModel & IHasBlockColor> extends ItemBlockModel<B>
        implements IHasItemColor {

    Material material;
    String key;

    public ItemBlockHeavyOre(B block, String key, Material material) {
        super(block);
        this.material = material;
        this.key = key;
    }

    @Override
    public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex) {
        return tintIndex == 0 ? getActualBlock().colorMultiplier(null, null, null, tintIndex) : -1;
    }

    // TODO
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if(I18n.canTranslate(material.getTranslationKey())) {
            String materialDisplayName = I18n.translateToLocal(material.getTranslationKey());
            return I18n.translateToLocalFormatted("steamagerevolution.part." + key, materialDisplayName);
        }
        else {
            return I18n.translateToLocalFormatted("steamagerevolution.part." + key, material.getName());
        }
    }
}
