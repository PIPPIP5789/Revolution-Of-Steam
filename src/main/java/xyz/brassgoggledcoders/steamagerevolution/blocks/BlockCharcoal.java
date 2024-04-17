package xyz.brassgoggledcoders.steamagerevolution.blocks;

import com.teamacronymcoders.base.blocks.BlockBase;

import net.minecraft.block.material.Material;
import xyz.brassgoggledcoders.steamagerevolution.items.itemblocks.ItemBlockCharcoal;

public class BlockCharcoal extends BlockBase {
    public BlockCharcoal() {
        super(Material.ROCK, "charcoal_block");
        setItemBlock(new ItemBlockCharcoal<>(this));
    }
}
