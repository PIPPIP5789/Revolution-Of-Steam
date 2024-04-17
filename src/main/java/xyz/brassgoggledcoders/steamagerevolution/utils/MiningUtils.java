package xyz.brassgoggledcoders.steamagerevolution.utils;

import java.lang.ref.WeakReference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class MiningUtils {

    public static boolean allowedToBreak(IBlockState state, World world, BlockPos pos, EntityPlayer entityPlayer) {
        if(!state.getBlock().canEntityDestroy(state, world, pos, entityPlayer)) {
            return false;
        }
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, entityPlayer);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    public static void doMining(World world, BlockPos pos, IItemHandler itemHandler) {
        // TODO Player tool
        WeakReference<FakePlayer> fakePlayer = new WeakReference<FakePlayer>(
                FakePlayerFactory.get((WorldServer) world, SteamAgeRevolution.profile));
        // Skip air, skip unbreakable blocks, skip tile entities and skip blocks that
        // are otherwise unharvestable
        IBlockState state = world.getBlockState(pos);
        if(!world.isAirBlock(pos) && state.getBlockHardness(world, pos) >= 0 && world.getTileEntity(pos) == null
                && allowedToBreak(state, world, pos, fakePlayer.get())) {
            state.getBlock().onBlockHarvested(world, pos, state, fakePlayer.get());
            state.getBlock().harvestBlock(world, fakePlayer.get(), pos, state, world.getTileEntity(pos),
                    new ItemStack(Items.IRON_PICKAXE));
            NonNullList<ItemStack> drops = NonNullList.create();
            for(EntityItem item : world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos).grow(1))) {
                ItemStack stack = item.getItem().copy();
                if(ItemHandlerHelper.insertItemStacked(itemHandler, stack, true).isEmpty()) {
                    ItemHandlerHelper.insertItemStacked(itemHandler, stack, false);
                    item.setDead();
                    drops.add(stack);
                }
            }
            world.destroyBlock(pos, false);
            ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, 0, 0, false, fakePlayer.get());
        }
    }

}
