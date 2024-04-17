package xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer;

import com.teamacronymcoders.base.multiblocksystem.IMultiblockPart;
import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBuilder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.ItemStackHandlerSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;

public class ControllerSteamHammer extends MultiblockCraftingMachine<InventoryCraftingMachine> {

    public static final String uid = "steam_hammer";

    public String dieType = "";
    BlockPos center = null;
    AxisAlignedBB interior = null;

    public ControllerSteamHammer(World world) {
        super(world);
        // TODO Switch to single handler for items?
        setInventory(new InventoryBuilder<>(new InventorySteamHammer(this))
                .addPiece("itemInput", new InventoryPieceItemHandler(IOType.INPUT, 35, 32))
                .addPiece("itemOutput", new InventoryPieceItemHandler(IOType.OUTPUT, 138, 32)).addSteamTank(9, 11)
                .addPiece("progress", new InventoryPieceProgressBar(103, 31)).build());
    }

    @Override
    public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
        dieType = data.getString("dieType");
        super.onAttachedPartWithMultiblockData(part, data);
    }

    @Override
    protected void onMachineAssembled() {
        center = getReferenceCoord().up().east().south();
        interior = new AxisAlignedBB(center).expand(1, 2, 1);
        WORLD.markBlockRangeForRenderUpdate(getMinimumCoord(), getMaximumCoord());
        super.onMachineAssembled();
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 26;// 3*3*4-10 Shielding is optional, always hollow.
    }

    @Override
    public int getMaximumXSize() {
        // TODO Auto-generated method stub
        return 3;
    }

    @Override
    public int getMaximumZSize() {
        // TODO Auto-generated method stub
        return 3;
    }

    @Override
    public int getMaximumYSize() {
        // TODO Auto-generated method stub
        return 4;
    }

    @Override
    public int getMinimumXSize() {
        return 3;
    }

    @Override
    public int getMinimumZSize() {
        return 3;
    }

    @Override
    public int getMinimumYSize() {
        return 4;
    }

    @Override
    protected void onTick() {
        for(EntityItem item : WORLD.getEntitiesWithinAABB(EntityItem.class, interior)) {
            if(ItemHandlerHelper.insertItem(getInventory().getHandler("itemInput", ItemStackHandlerSync.class),
                    item.getItem(), true).isEmpty()) {
                ItemHandlerHelper.insertItem(getInventory().getHandler("itemInput", ItemStackHandlerSync.class),
                        item.getItem(), false);
                item.setDead();
            }
        }
        super.onTick();
    }

    public void onFinish() {
        WORLD.playSound(null, center.getX() + .5F, center.getY(), center.getZ() + .5F, SoundEvents.BLOCK_ANVIL_LAND,
                SoundCategory.BLOCKS, 1F, 1F);
        SteamAgeRevolution.proxy.spawnFX(EnumParticleTypes.FLAME, center);
        WORLD.getEntitiesWithinAABB(EntityLivingBase.class, interior)
                .forEach(entity -> entity.attackEntityFrom(SARBlocks.damageSourceHammer, entity.getMaxHealth()));
    }

    @Override
    protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        return world.isAirBlock(new BlockPos(x, y, z));
    }

    @Override
    public void writeToDisk(NBTTagCompound data) {
        data.setString("dieType", dieType);
        super.writeToDisk(data);
    }

    @Override
    public MultiblockMachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MultiblockMachineType(uid, SARObjectHolder.steamhammer_anvil));
        }
        return (MultiblockMachineType) MachineType.machinesList.get(uid);
    }
}
