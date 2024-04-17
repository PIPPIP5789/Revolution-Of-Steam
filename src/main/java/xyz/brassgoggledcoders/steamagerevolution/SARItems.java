package xyz.brassgoggledcoders.steamagerevolution;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.api.ILens;
import xyz.brassgoggledcoders.steamagerevolution.items.*;
import xyz.brassgoggledcoders.steamagerevolution.items.tools.*;

public class SARItems {
    public static ArrayList<ILens> lenseTypes = Lists.newArrayList();
    public static final ToolMaterial STEAM = EnumHelper.addToolMaterial("TOOL_STEAM", 2, -1, 12.0F, 3.0F, 0);
    public static final ArmorMaterial GOGGLES = EnumHelper.addArmorMaterial("ARMOR_GOGGLES", "goggles", -1,
            new int[] { 1, 2, 3, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    // For Drill blacklisting
    public static final HashSet<Block> KNOWN_ORES = new HashSet<Block>();

    public static void registerItems(ItemRegistry itemRegistry) {
        itemRegistry.register(new ItemCanister("canister", Fluid.BUCKET_VOLUME * 8));

        itemRegistry.register(new ItemBase("charcoal_powder"));
        itemRegistry.register(new ItemFlask("flask", Fluid.BUCKET_VOLUME * 3));

        itemRegistry.register(new ItemMinecartCarrier());
        itemRegistry.register(new ItemMinecartDrilling());

        itemRegistry.register(new ItemClockworkWings());

        itemRegistry.register(new ItemSteamPickaxe("steam_pickaxe", 1000));
        itemRegistry.register(new ItemSteamAxe("steam_axe", 1000));
        itemRegistry.register(new ItemSteamShovel("steam_shovel", 1000));
        itemRegistry.register(new ItemSteamHoe("steam_hoe", 1000));
        itemRegistry.register(new ItemSteamSword("steam_sword", 1000));

        itemRegistry.register(new ItemRocketFist());

        itemRegistry.register(new ItemBase("drill_base"));
        itemRegistry.register(new ItemDrill("stone_drill", ToolMaterial.STONE));
        itemRegistry.register(new ItemDrill("iron_drill", ToolMaterial.IRON));
        itemRegistry.register(new ItemDrill("gold_drill", ToolMaterial.GOLD));
        itemRegistry.register(new ItemDrill("diamond_drill", ToolMaterial.DIAMOND));
        // Steel tools?
        // TODO Add drills from other mods materials and ability for other mods to
        // register drills through IMC

        itemRegistry.register(new ItemLens());
        itemRegistry.register(new ItemGoggles());

        itemRegistry.register(new ItemEntrenchingTool("entrenching_tool", ToolMaterial.IRON));

        itemRegistry.register(new ItemHammer());
        itemRegistry.register(new ItemDie());
    }
}
