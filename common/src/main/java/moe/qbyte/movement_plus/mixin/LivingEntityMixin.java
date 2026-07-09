package moe.qbyte.movement_plus.mixin;

import moe.qbyte.movement_plus.EntityHooks;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "jumpFromGround", at = @At("TAIL"))
    private void movement_plus$afterJump(CallbackInfo ci) {
        EntityHooks.onLivingJump((LivingEntity) (Object) this);
    }

    @ModifyVariable(method = "causeFallDamage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float movement_plus$modifyFallDistance(float distance) {
        return EntityHooks.modifyFallDistance((LivingEntity) (Object) this, distance);
    }
}
