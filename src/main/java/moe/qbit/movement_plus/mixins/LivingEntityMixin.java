package moe.qbit.movement_plus.mixins;

import moe.qbit.movement_plus.common.attributes.ModAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    private LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
        throw new IllegalStateException("Tried to create Mixin class object.");
    }

    @Shadow
    public double getAttributeValue(Attribute p_21134_) {
        throw new IllegalStateException("Mixin failed to shadow getAttributeValue()");
    }


    /**
     * @author Movement Plus
     * @reason Make jump power based on an attribute.
     */
    @Overwrite
    protected float getJumpPower() {
        return (float) (this.getAttributeValue(ModAttributes.JUMP_HEIGHT.get()) * this.getBlockJumpFactor());
    }
}
