package xyz.brassgoggledcoders.steamagerevolution.utils;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;

public class EntityAIAttackRangedGeneric<T extends EntityMob & IRangedAttackMob> extends EntityAIAttackRangedBow<T> {
    T entity;
    Item required;

    public EntityAIAttackRangedGeneric(T mob, double moveSpeedAmpIn, int attackCooldownIn, float maxAttackDistanceIn,
            Item holdRequirement) {
        super(mob, moveSpeedAmpIn, attackCooldownIn, maxAttackDistanceIn);
        this.entity = mob;
        this.required = holdRequirement;
    }

    @Override
    protected boolean isBowInMainhand() {
        return !this.entity.getHeldItemMainhand().isEmpty() && this.entity.getHeldItemMainhand().getItem() == required;
    }
}
