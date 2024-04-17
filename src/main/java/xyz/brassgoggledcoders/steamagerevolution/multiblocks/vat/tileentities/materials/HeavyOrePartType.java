package xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities.materials;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.materialsystem.MaterialUser;
import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.base.materialsystem.parttype.BlockPartType;
import com.teamacronymcoders.base.materialsystem.parttype.PartDataPiece;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.blocks.BlockHeavyOre;
import xyz.brassgoggledcoders.steamagerevolution.blocks.BlockHeavyOreIndicator;
import xyz.brassgoggledcoders.steamagerevolution.worldgen.OreGenerator;

public class HeavyOrePartType extends BlockPartType {
    public HeavyOrePartType() {
        this("heavy_ore");
    }

    public HeavyOrePartType(String name) {
        this(name, new ArrayList<>());
    }

    public HeavyOrePartType(String name, List<PartDataPiece> dataPieces) {
        super(name, setupData(dataPieces));
    }

    private static List<PartDataPiece> setupData(List<PartDataPiece> blockDataPieces) {
        blockDataPieces.add(new PartDataPiece("hardness", false));
        blockDataPieces.add(new PartDataPiece("resistance", false));
        blockDataPieces.add(new PartDataPiece("harvestLevel", false));
        blockDataPieces.add(new PartDataPiece("harvestTool", false));
        return blockDataPieces;
    }

    @Override
    public void setup(@Nonnull MaterialPart materialPart, @Nonnull MaterialUser materialUser) {
        String name = materialPart.getMaterial().getOreDictSuffix();
        BlockHeavyOre ore = new BlockHeavyOre(materialPart, name);
        BlockRegistry registry = materialUser.getMod().getRegistryHolder().getRegistry(BlockRegistry.class, "BLOCK");
        registry.register(ore);
        registry.register(new BlockHeavyOreIndicator(ore, materialPart));
        OreGenerator.heavyOresToGenerate.add(name);
    }

    @Override
    public ItemStack getItemStack(MaterialPart materialPart) {
        BlockRegistry registry = materialPart.getMaterialUser().getMod().getRegistryHolder()
                .getRegistry(BlockRegistry.class, "BLOCK");
        ItemStack itemStack = ItemStack.EMPTY;
        if(registry.get(new ResourceLocation(materialPart.getMaterialUser().getMod().getID(),
                materialPart.getUnlocalizedName())) != null) {
            itemStack = new ItemStack(Item
                    .getItemFromBlock(registry.get(new ResourceLocation(materialPart.getMaterialUser().getMod().getID(),
                            materialPart.getUnlocalizedName()))));
        }
        return itemStack;
    }
}
