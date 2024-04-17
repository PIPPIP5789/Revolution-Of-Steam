package xyz.brassgoggledcoders.steamagerevolution.items;

import java.util.List;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.items.ItemArmorBase;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.client.ModelClockworkWings;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketIncreaseHunger;

public class ItemClockworkWings extends ItemArmorBase {

    public static final float hungerPerTick = 1F;

    public ItemClockworkWings() {
        super(ArmorMaterial.LEATHER, EntityEquipmentSlot.CHEST, "clockwork_wings");
        setMaxDamage(0);
        setTranslationKey("clockwork_wings");
    }

    // TODO
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        if(world.isRemote && !player.capabilities.allowFlying && (player.getFoodStats().getFoodLevel() != 0)) {
            if((player.motionY < 0.0D) && player.isSneaking() && !player.onGround) {
                SteamAgeRevolution.instance.getPacketHandler()
                        .sendToServer(new PacketIncreaseHunger(ItemClockworkWings.hungerPerTick / 2));
                // Position is set serverside, velocity is set clientside which then auto
                // updates the serverside position. Weird.
                player.setVelocity(player.motionX *= 1.05D, player.motionY /= 1.4D, player.motionZ *= 1.05D);
            }
        }
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack is, Entity entity, EntityEquipmentSlot slot, String type) {
        return SteamAgeRevolution.MODID + ":textures/models/armor/wings.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack,
            EntityEquipmentSlot armorSlot, net.minecraft.client.model.ModelBiped _default) {
        return new ModelClockworkWings(1F);
    }

    @Override
    public Item getItem() {
        return this;
    }

    @Override
    public List<String> getModelNames(List<String> modelNames) {
        modelNames.add("clockwork_wings");
        return modelNames;
    }
}
