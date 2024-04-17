package xyz.brassgoggledcoders.steamagerevolution.entities.render;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.renderer.entity.minecart.RenderMinecartBase;
import com.teamacronymcoders.base.util.RenderingUtils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.entities.EntityMinecartCarrier;

public class RenderMinecartCarrier extends RenderMinecartBase<EntityMinecartCarrier> {
    private static final ResourceLocation TEX = new ResourceLocation(SteamAgeRevolution.MODID,
            "textures/models/carrier_cart.png");
    protected ModelCarrierMinecart model = new ModelCarrierMinecart();

    public RenderMinecartCarrier(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void doRender(@Nonnull EntityMinecartCarrier entity, double x, double y, double z, float entityYaw,
            float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        // ItemStack stack =
        // entity.getDataManager().get(EntityMinecartCarrier.CONTENTS);
        // if (!stack.isEmpty()) {
        // GlStateManager.pushMatrix();
        // RenderingUtils.pre(x, y, z);
        // int brightness = entity.getBrightnessForRender();
        // OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
        // brightness % 0x10000 / 1f,
        // brightness / 0x10000 / 1f);
        //
        // GlStateManager.translate(0.5F, 1f, -0.5F);
        // GlStateManager.scale(0.5f, 0.5f, 0.5f);
        //
        // if (!(stack.getItem() instanceof ItemBlock)
        // || Block.getBlockFromItem(stack.getItem()) instanceof BlockPane) {
        // GlStateManager.rotate(-90, 1, 0, 0);
        // }
        //
        // GL11.glDepthMask(false);
        // IBakedModel model =
        // Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack,
        // entity.getEntityWorld(), null);
        // Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
        // GL11.glDepthMask(true);
        // RenderingUtils.post();
        // GlStateManager.popMatrix();
        // }
    }

    @Override
    protected void renderCartModel(EntityMinecartCarrier entity) {
        model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        ItemStack stack = entity.getDataManager().get(EntityMinecartCarrier.CONTENTS);
        if(!stack.isEmpty()) {
            int color = 0;
            for(int tintIndex = 0; tintIndex < 3; tintIndex++) {
                if(Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, tintIndex) != -1) {
                    color = Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, tintIndex);
                    break;
                }
                else if(Minecraft.getMinecraft().getBlockColors().colorMultiplier(
                        Block.getBlockFromItem(stack.getItem()).getDefaultState(), entity.getEntityWorld(),
                        entity.getPosition(), tintIndex) != -1) {
                    color = Minecraft.getMinecraft().getBlockColors().colorMultiplier(
                            Block.getBlockFromItem(stack.getItem()).getDefaultState(), entity.getEntityWorld(),
                            entity.getPosition(), tintIndex);
                    break;
                }
            }
            GlStateManager.pushMatrix();
            RenderingUtils.pre(0, 0, 0);
            float red = (color >> 16 & 255) / 255.0F;
            float green = (color >> 8 & 255) / 255.0F;
            float blue = (color & 255) / 255.0F;
            GlStateManager.color(red, green, blue);
            model.renderContents(0.0625F);
            RenderingUtils.post();
            GlStateManager.popMatrix();
            GlStateManager.color(1F, 1F, 1F);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMinecartCarrier entity) {
        return TEX;
    }
}
