package xyz.brassgoggledcoders.steamagerevolution.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.*;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.items.tools.ItemRocketFist;
import xyz.brassgoggledcoders.steamagerevolution.utils.EntityAIAttackRangedGeneric;

public class EntityDeadInventor extends EntitySkeleton {

    private final EntityAIAttackRangedGeneric<EntityDeadInventor> aiRocket = new EntityAIAttackRangedGeneric<EntityDeadInventor>(
            this, 1.0D, 20, 30.0F, SARObjectHolder.rocket_fist);
    private final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false);

    public EntityDeadInventor(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        int minSteam = Math.round(50 * difficulty.getAdditionalDifficulty());
        ItemStack stack = new ItemStack(SARObjectHolder.rocket_fist);
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        internal.fill(FluidRegistry.getFluidStack("steam", minSteam + rand.nextInt(ItemRocketFist.capacity - minSteam)),
                true);
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
        setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(SARObjectHolder.goggles));
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if(!(entityIn instanceof EntityLivingBase)) { // Wut
            return false;
        }
        ItemStack stack = getHeldItemMainhand();
        if(!stack.isEmpty()) {
            // Of course I have to manually call that...despite the fact the Entity instance
            // in the method
            // is generic
            stack.getItem().hitEntity(stack, (EntityLivingBase) entityIn, this);
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public void setCombatTask() {
        if(world != null && !world.isRemote) {
            tasks.removeTask(aiRocket);
            ItemStack itemstack = getHeldItemMainhand();

            if(itemstack.getItem() == SARObjectHolder.rocket_fist) {
                int i = 20;

                if(world.getDifficulty() != EnumDifficulty.HARD) {
                    i = 40;
                }

                aiRocket.setAttackCooldown(i);
                tasks.addTask(4, aiRocket);
            }
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        ItemStack stack = getHeldItemMainhand();
        // Shouldn't need the second check because its already done in AI but you never
        // know
        if(!stack.isEmpty() && stack.getItem() == SARObjectHolder.rocket_fist) {
            ((ItemRocketFist) stack.getItem()).doFistBoost(this, stack, 0); // TODO Set charge factor thing
            // TODO Make two tasks compatible
            tasks.removeTask(aiRocket);
            tasks.addTask(5, aiAttackOnCollide);
        }
    }
}
