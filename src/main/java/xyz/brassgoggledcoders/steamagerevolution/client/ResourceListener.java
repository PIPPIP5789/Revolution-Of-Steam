package xyz.brassgoggledcoders.steamagerevolution.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public class ResourceListener implements IResourceManagerReloadListener {
    public final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
        for(int i = 0; i < destroyBlockIcons.length; ++i) {
            destroyBlockIcons[i] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + i);
        }
    }
}
