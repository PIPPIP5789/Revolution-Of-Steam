package xyz.brassgoggledcoders.steamagerevolution.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ILens {

    public int getColor();

    public String getColorName();

    public void onArmorTick(World world, EntityPlayer player, ItemStack stack);

    public String getEffect();
}
