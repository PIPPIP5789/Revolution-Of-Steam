package xyz.brassgoggledcoders.steamagerevolution.items;

import com.teamacronymcoders.base.entities.EntityMinecartBase;
import com.teamacronymcoders.base.items.minecart.ItemMinecartBase;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.entities.EntityMinecartDrilling;

public class ItemMinecartDrilling extends ItemMinecartBase {

    public ItemMinecartDrilling() {
        super("drilling");
    }

    @Override
    public EntityMinecartBase getEntityFromItem(World world, ItemStack itemStack) {
        return new EntityMinecartDrilling(world);
    }

}
