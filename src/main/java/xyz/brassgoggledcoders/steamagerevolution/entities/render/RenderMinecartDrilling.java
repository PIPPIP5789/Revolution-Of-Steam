package xyz.brassgoggledcoders.steamagerevolution.entities.render;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Optional;
import com.teamacronymcoders.base.renderer.entity.minecart.RenderMinecartBase;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.entities.EntityMinecartDrilling;
import xyz.brassgoggledcoders.steamagerevolution.proxies.ClientProxy;

public class RenderMinecartDrilling extends RenderMinecartBase<EntityMinecartDrilling> {

    private static final ResourceLocation TEX = new ResourceLocation(SteamAgeRevolution.MODID,
            "textures/models/drill_cart.png");
    protected ModelBase model = new ModelDrillingMinecart();

    public RenderMinecartDrilling(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void doRender(@Nonnull EntityMinecartDrilling entity, double x, double y, double z, float entityYaw,
            float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        Optional<BlockPos> blockPos = entity.getDataManager().get(EntityMinecartDrilling.MINING_POS);
        if(blockPos.isPresent()) {
            drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), blockPos.get(),
                    partialTicks, entity.getEntityWorld(),
                    entity.getDataManager().get(EntityMinecartDrilling.MINING_PROGRESS).floatValue());
        }
    }

    @Override
    protected void renderCartModel(EntityMinecartDrilling entity) {
        model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMinecartDrilling entity) {
        return TEX;
    }

    // Copied from TiCon (who copied it from Vanilla's RenderManager)
    public void drawBlockDamageTexture(Tessellator tessellatorIn, BufferBuilder bufferBuilder, BlockPos pos,
            float partialTicks, World world, float progressIn) {
        TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
        IBlockState iblockstate = world.getBlockState(pos);
        int progress = (int) Math.floor((progressIn * 10F) - 1); // 0-10

        if(progress < 0) {
            return;
        }

        renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        // preRenderDamagedBlocks BEGIN
        GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        GlStateManager.doPolygonOffset(-3.0F, -3.0F);
        GlStateManager.enablePolygonOffset();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableAlpha();
        GlStateManager.pushMatrix();
        // preRenderDamagedBlocks END

        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        // Don't try and translate to where we want to render, instead translate
        // relative to the player for viewport
        Entity entityIn = Minecraft.getMinecraft().player;
        double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
        double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
        double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
        bufferBuilder.setTranslation(-d0, -d1, -d2);
        bufferBuilder.noColor();

        Block block = world.getBlockState(pos).getBlock();
        TileEntity te = world.getTileEntity(pos);
        boolean hasBreak = block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSign
                || block instanceof BlockSkull;
        if(!hasBreak) {
            hasBreak = te != null && te.canRenderBreaking();
        }

        if(!hasBreak) {
            if(!iblockstate.getBlock().isAir(iblockstate, world, pos)) {
                TextureAtlasSprite textureatlassprite = ((ClientProxy) SteamAgeRevolution.proxy).listener.destroyBlockIcons[progress];
                BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                blockrendererdispatcher.renderBlockDamage(iblockstate, pos, textureatlassprite, world);
            }
        }

        tessellatorIn.draw();
        bufferBuilder.setTranslation(0.0D, 0.0D, 0.0D);
        // postRenderDamagedBlocks BEGIN
        GlStateManager.disableAlpha();
        GlStateManager.doPolygonOffset(0.0F, 0.0F);
        GlStateManager.disablePolygonOffset();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
        // postRenderDamagedBlocks END
    }
}
