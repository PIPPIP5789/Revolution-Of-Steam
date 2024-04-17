package xyz.brassgoggledcoders.steamagerevolution;

import com.teamacronymcoders.base.client.ClientHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.items.ItemClockworkWings;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketIncreaseHunger;

@EventBusSubscriber(value = Side.CLIENT, modid = SteamAgeRevolution.MODID)
public class EventHandlerClient {

    public static TextureAtlasSprite test;

    // TODO Remember this fires on release
    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        if(GameSettings.isKeyDown(ClientHelper.settings().keyBindJump)) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if(player.posY < 160 && player.getFoodStats().getFoodLevel() != 0
                    && player.inventory.armorInventory.get(2).getItem() == SARObjectHolder.wings) {
                player.setVelocity(player.motionX, player.motionY + 0.3F, player.motionZ);
                SteamAgeRevolution.instance.getPacketHandler()
                        .sendToServer(new PacketIncreaseHunger(ItemClockworkWings.hungerPerTick));
            }
        }
    }

    @SubscribeEvent
    public static void onDrawBlockSelectionBox(DrawBlockHighlightEvent event) {
        if((event.getPlayer().inventory.armorItemInSlot(3) != null)
                && (event.getPlayer().inventory.armorItemInSlot(3).getItem() == SARObjectHolder.goggles)) {
            drawSelectionBox(event.getPlayer(), event.getTarget(), event.getPartialTicks());
            event.setCanceled(true);
        }
    }

    private static void drawSelectionBox(EntityPlayer player, RayTraceResult mop, float partialTicks) {
        if(mop.typeOfHit == RayTraceResult.Type.BLOCK) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                    GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO);
            GlStateManager.glLineWidth(5.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            float offset = 0.002F;
            World world = player.world;
            BlockPos pos = mop.getBlockPos();
            IBlockState state = world.getBlockState(pos);

            if(!world.isAirBlock(pos)) {
                double dx = player.lastTickPosX + ((player.posX - player.lastTickPosX) * partialTicks);
                double dy = player.lastTickPosY + ((player.posY - player.lastTickPosY) * partialTicks);
                double dz = player.lastTickPosZ + ((player.posZ - player.lastTickPosZ) * partialTicks);
                drawSelectionBoundingBox(
                        state.getSelectedBoundingBox(world, pos).expand(offset, offset, offset).offset(-dx, -dy, -dz),
                        0.2F, 1F, 0.2F, 0.8F);
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
        else if(mop.typeOfHit == RayTraceResult.Type.ENTITY) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                    GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO);
            GlStateManager.glLineWidth(5.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            float offset = 0.002F;
            Entity entity = mop.entityHit;

            if(entity != null) {
                entity.setPosition(entity.posX, entity.posY, entity.posZ);
                double dx = player.lastTickPosX + ((player.posX - player.lastTickPosX) * partialTicks);
                double dy = player.lastTickPosY + ((player.posY - player.lastTickPosY) * partialTicks);
                double dz = player.lastTickPosZ + ((player.posZ - player.lastTickPosZ) * partialTicks);
                drawSelectionBoundingBox(
                        entity.getEntityBoundingBox().expand(offset, offset, offset).offset(-dx, -dy, -dz), 0.2F, 1F,
                        0.2F, 0.8F);
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB box, float red, float green, float blue, float alpha) {
        drawBoundingBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);
    }

    public static void drawBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ,
                                       float red, float green, float blue, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        drawBoundingBox(bufferbuilder, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
        tessellator.draw();
    }

    public static void drawBoundingBox(BufferBuilder buffer, double minX, double minY, double minZ, double maxX,
                                       double maxY, double maxZ, float red, float green, float blue, float alpha) {
        buffer.pos(minX, minY, minZ).color(red, green, blue, 0.0F).endVertex();
        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, maxZ).color(red, green, blue, 0.0F).endVertex();
        buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, maxZ).color(red, green, blue, 0.0F).endVertex();
        buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, minZ).color(red, green, blue, 0.0F).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, 0.0F).endVertex();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(receiveCanceled = true)
    public static void fogEvent(FogDensity event) {
        Entity entity = event.getEntity();
        if(entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            // if(player.isInsideOfMaterial(Material.WATER))
            ItemStack stack = player.inventory.armorInventory.get(3);
            if(!stack.isEmpty() && stack.getItem() == SARObjectHolder.goggles) {
                event.setCanceled(true);
                if(!stack.hasTagCompound()) {
                    stack.setTagCompound(new NBTTagCompound());
                }
                if(player.isInsideOfMaterial(Material.WATER)
                        && stack.getTagCompound().getBoolean("lens" + EnumDyeColor.LIGHT_BLUE.getMetadata())) {
                    event.setDensity(0.0F);
                }
                else if(player.isInsideOfMaterial(Material.LAVA)
                        && stack.getTagCompound().getBoolean("lens" + EnumDyeColor.ORANGE.getMetadata())) {
                    event.setDensity(0.0F);
                }
                else if(stack.getTagCompound().getBoolean("lens" + EnumDyeColor.BLACK.getMetadata())) {
                    event.setDensity(0.0F);
                }
            }
        }
    }
}