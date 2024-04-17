package xyz.brassgoggledcoders.steamagerevolution.items;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import com.teamacronymcoders.base.client.ClientHelper;
import com.teamacronymcoders.base.items.ItemArmorBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
//import thaumcraft.api.items.IGoggles;
import xyz.brassgoggledcoders.steamagerevolution.SARItems;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Optional.Interface(iface = "thaumcraft.api.items.IGoggles", modid = "thaumcraft")
public class ItemGoggles extends ItemArmorBase /*implements IGoggles*/ {

    public static ResourceLocation overlay = new ResourceLocation(SteamAgeRevolution.MODID,
            "textures/misc/goggles.png");
    public static ResourceLocation overlay2 = new ResourceLocation(SteamAgeRevolution.MODID,
            "textures/misc/goggles_2.png");

    public ItemGoggles() {
        super(SARItems.GOGGLES, EntityEquipmentSlot.HEAD, "goggles");
        setMaxStackSize(1);
    }

    @SuppressWarnings("deprecation")
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("Lenses:");
        if(!stack.hasTagCompound()) {
            return;
        }
        for(int i = 0; i < SARItems.lenseTypes.size(); i++) {
            if(stack.getTagCompound().getBoolean("lens" + i)) {
                // TODO tooltip text colours
                tooltip.add(WordUtils
                        .capitalize(I18n.translateToLocal(SARItems.lenseTypes.get(i).getColorName())));
            }
        }
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        for(int i = 0; i < SARItems.lenseTypes.size(); i++) {
            if(stack.getTagCompound().getBoolean("lens" + i)) {
                SARItems.lenseTypes.get(i).onArmorTick(world, player, stack);
            }
        }
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack is, Entity entity, EntityEquipmentSlot slot, String type) {
        return SteamAgeRevolution.MODID + ":textures/models/armor/goggles.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution,
            float partialTicks) {
        if((ClientHelper.player() == null) || (ClientHelper.screen() != null)) {
            return;
        }

        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
        final int i = resolution.getScaledWidth();
        final int k = resolution.getScaledHeight();
        // TODO Add a colour overlay based on what lenses are installed
        // Gui.drawRect(0, 0, i, k, EnumDyeColor.BLUE.getColorValue());
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        for(int len = 0; len < SARItems.lenseTypes.size(); len++) {
            if(stack.getTagCompound().getBoolean("lens" + EnumDyeColor.CYAN.getMetadata())) {
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(overlay2);
                break;
            }
            else {
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(overlay);
                continue;
            }
        }

        final Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        tessellator.getBuffer().pos(i / 2 - k, k, -90D).tex(0.0D, 1.0D).endVertex();
        tessellator.getBuffer().pos(i / 2 + k, k, -90D).tex(1.0D, 1.0D).endVertex();
        tessellator.getBuffer().pos(i / 2 + k, 0.0D, -90D).tex(1.0D, 0.0D).endVertex();
        tessellator.getBuffer().pos(i / 2 - k, 0.0D, -90D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

    }

    @Optional.Method(modid = "thaumcraft")
    //@Override
    public boolean showIngamePopups(ItemStack stack, EntityLivingBase arg1) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound().getBoolean("lens" + EnumDyeColor.PURPLE.getMetadata());
    }

}
