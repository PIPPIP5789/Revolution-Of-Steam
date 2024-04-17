package xyz.brassgoggledcoders.steamagerevolution.items;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.text.WordUtils;

import com.teamacronymcoders.base.items.IHasSubItems;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SARItems;
import xyz.brassgoggledcoders.steamagerevolution.api.ILens;
import xyz.brassgoggledcoders.steamagerevolution.api.VanillaLens;

public class ItemLens extends ItemBase implements IHasSubItems {

    public ItemLens() {
        super("lens");
        setHasSubtypes(true);
        setMaxDamage(0);
        registerVanillaLenses();
    }

    private void registerVanillaLenses() {
        for(int i = 0; i < 16; ++i) {
            if(i == EnumDyeColor.GREEN.getMetadata()) {
                SARItems.lenseTypes.add(new VanillaLens(i) {
                    // TODO Switch to a brightness increaser instead of a potion effect
                    @Override
                    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
                        // Has to be a long time to avoid the fucking flicker bollocks
                        player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"),
                                500, 0, true, false));
                    }

                    @Override
                    public String getEffect() {
                        return "Grants night vision";
                    }
                });
            }
            else if(i == EnumDyeColor.CYAN.getMetadata()) {
                SARItems.lenseTypes.add(new VanillaLens(i) {
                    @Override
                    public String getEffect() {
                        return "Lessens the goggle overlay";
                    }
                });
            }
            else if(i == EnumDyeColor.SILVER.getMetadata()) {
                SARItems.lenseTypes.add(new VanillaLens(i) {
                    @Override
                    public String getEffect() {
                        return "Makes the goggles act as a OneProbe probe";
                    }
                });
            }
            else if(i == EnumDyeColor.WHITE.getMetadata()) {
                SARItems.lenseTypes.add(new VanillaLens(i) {
                    @Override
                    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
                        // TODO Switch to cancelling an event
                        player.removePotionEffect(Potion.getPotionFromResourceLocation("blindness"));
                    }

                    @Override
                    public String getEffect() {
                        return "Protects you against blindness";
                    }
                });
            }
            else if(i == EnumDyeColor.LIGHT_BLUE.getMetadata()) {
                SARItems.lenseTypes.add(new VanillaLens(i) {
                    @Override
                    public String getEffect() {
                        return "Removes water fog";
                    }
                });
            }
            else if(i == EnumDyeColor.ORANGE.getMetadata()) {
                SARItems.lenseTypes.add(new VanillaLens(i) {
                    @Override
                    public String getEffect() {
                        return "Removes lava fog";
                    }
                });
            }
            else if(i == EnumDyeColor.BLACK.getMetadata()) {
                SARItems.lenseTypes.add(new VanillaLens(i) {
                    @Override
                    public String getEffect() {
                        return "Removes world fog";
                    }
                });
            }
            else if(i == EnumDyeColor.PURPLE.getMetadata()) {
                SARItems.lenseTypes.add(new VanillaLens(i) {
                    @Override
                    public String getEffect() {
                        return "Makes the goggles act as Goggles of Revealing";
                    }
                });
            }
            else {
                SARItems.lenseTypes.add(new VanillaLens(i));
            }
        }
    }

    @Override
    public List<String> getModelNames(List<String> modelNames) {
        modelNames.add(name);
        return modelNames;
    }

    @Override
    public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
        // TODO This is likely a fragile way of storing, but it feels unnecessary to use
        // a hashmap
        int i = 0;
        for(ILens lens : SARItems.lenseTypes) {
            itemStacks.add(new ItemStack(this, 1, i++));
        }
        return itemStacks;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("Effect:");
        tooltip.add(SARItems.lenseTypes.get(stack.getMetadata()).getEffect());
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return WordUtils.capitalize(
                I18n.translateToLocal(SARItems.lenseTypes.get(stack.getMetadata()).getColorName())) + " "
                + super.getItemStackDisplayName(stack);
    }
}
