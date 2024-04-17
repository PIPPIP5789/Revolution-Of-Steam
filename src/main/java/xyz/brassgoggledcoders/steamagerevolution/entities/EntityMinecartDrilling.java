package xyz.brassgoggledcoders.steamagerevolution.entities;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Optional;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase.EnumRailDirection;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemMinecart;
import net.minecraft.network.datasync.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBuilder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.utils.MiningUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.ContainerForceStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.HandlerForceStack;

public class EntityMinecartDrilling extends EntityMinecartInventory<InventoryBasic> {

    public static final String uid = "drilling_cart";

    public static final DataParameter<Optional<BlockPos>> MINING_POS = EntityDataManager
            .<Optional<BlockPos>>createKey(EntityMinecartDrilling.class, DataSerializers.OPTIONAL_BLOCK_POS);
    public static final DataParameter<Float> MINING_PROGRESS = EntityDataManager
            .<Float>createKey(EntityMinecartDrilling.class, DataSerializers.FLOAT);

    public EntityMinecartDrilling(World world) {
        super(world);
        Pair<int[], int[]> posi = GUIInventory.getGUIPositionGrid(62, 31, 3, 1);
        setInventory(new InventoryBuilder<>(new InventoryBasic(this))
                .addPiece("inventory",
                        new InventoryPieceItemHandler(new HandlerForceStack(3), posi.getLeft(), posi.getRight()))
                .build());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        return new GUIInventory(entityPlayer, this, new ContainerForceStack(entityPlayer, this));
    }

    @Override
    public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        return new ContainerForceStack(entityPlayer, this);
    }

    @Override
    public ItemMinecart getItem() {
        return SARObjectHolder.minecart_drilling;
    }

    @Override
    protected void entityInit() {
        dataManager.register(MINING_POS, Optional.absent());
        dataManager.register(MINING_PROGRESS, 0F);
        super.entityInit();
    }

    @Override
    public void onActivatorRailPass(int blockX, int blockY, int blockZ, boolean receivingPower) {
        if(receivingPower) {
            BlockPos pos = new BlockPos(blockX, blockY, blockZ);
            if(BlockRailBase.isRailBlock(world, pos)) {
                BlockRailBase rail = (BlockRailBase) getEntityWorld().getBlockState(pos).getBlock();
                EnumRailDirection direction = rail.getRailDirection(getEntityWorld(), pos,
                        getEntityWorld().getBlockState(pos), this);
                EnumFacing cartFacing = getAdjustedHorizontalFacing();
                switch(direction) {
                    case NORTH_SOUTH:
                        if(EnumFacing.NORTH.equals(cartFacing)) {
                            doMining(EnumFacing.WEST);
                        }
                        else {
                            doMining(EnumFacing.EAST);
                        }
                        break;
                    case EAST_WEST:
                        if(EnumFacing.WEST.equals(cartFacing)) {
                            doMining(EnumFacing.NORTH);
                        }
                        else {
                            doMining(EnumFacing.SOUTH);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void doMining(EnumFacing facingToMine) {
        WeakReference<FakePlayer> fakePlayer = new WeakReference<FakePlayer>(
                FakePlayerFactory.get((WorldServer) world, SteamAgeRevolution.profile));
        BlockPos target = getPosition().offset(facingToMine);
        if(!world.isAirBlock(target)) {
            if(getDataManager().get(MINING_PROGRESS).floatValue() >= 1.0F) {
                MiningUtils.doMining(getEntityWorld(), target, getInventory().getItemHandlers().get(0));
                getDataManager().set(MINING_POS, Optional.absent());
                getDataManager().set(MINING_PROGRESS, 0F);
                return;
            }
            else {
                getDataManager().set(MINING_POS, Optional.of(target));
                getDataManager().set(MINING_PROGRESS, getDataManager().get(MINING_PROGRESS)
                        + world.getBlockState(target).getPlayerRelativeBlockHardness(fakePlayer.get(), world, target));
                return;
            }
        }
        // If we can't mine, reset. TODO Reset on cart move?
        getDataManager().set(MINING_PROGRESS, 0F);
    }

    @Override
    public void markMachineDirty() {
        markDirty();
    }

    @Override
    public MachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MachineType(uid));
        }
        return MachineType.machinesList.get(uid);
    }

}
