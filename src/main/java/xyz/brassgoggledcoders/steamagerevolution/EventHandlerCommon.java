package xyz.brassgoggledcoders.steamagerevolution;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities.materials.ModuleMaterials;

@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class EventHandlerCommon {
    @SubscribeEvent
    public static void onOreRegistered(OreDictionary.OreRegisterEvent event) {
        String name = event.getName();
        String[] splitName = name.split("(?=[A-Z])");
        if(splitName.length == 2) {
            if(splitName[0].equals("ingot")) {
                String metalType = splitName[1];
                if(!ModuleMaterials.knownMetalTypes.contains(metalType)) {
                    ModuleMaterials.knownMetalTypes.add(metalType);
                    SteamAgeRevolution.instance.getLogger().devInfo("Metal type detected: " + metalType);
                }
            }
        }
        if(event.getName().contains("ore")) {
            SARItems.KNOWN_ORES.add(Block.getBlockFromItem(event.getOre().getItem()));
        }
    }

    // No fall damage while wearing wings
    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if((player.getFoodStats().getFoodLevel() != 0)
                    && player.inventory.armorInventory.get(2).getItem() == SARObjectHolder.wings) {
                // player.addExhaustion(ItemClockworkWings.hungerPerTick / 4); TODO
                event.setDamageMultiplier(0);
            }
        }
    }

    // Acidic fluids deal extra damage to armour
    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent event) {
        if(event.getSource() == SARBlocks.damageSourceAcid || event.getSource() == SARBlocks.damageSourceGas) {
            EntityLivingBase living = event.getEntityLiving();
            living.getArmorInventoryList()
                    .forEach(stack -> stack.damageItem(living.getEntityWorld().rand.nextInt(5), living));
        }
    }
}
