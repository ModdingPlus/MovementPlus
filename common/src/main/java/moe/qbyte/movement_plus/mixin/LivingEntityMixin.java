package moe.qbyte.movement_plus.mixin;

import moe.qbyte.movement_plus.EntityHooks;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
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

    // ordinal 0 is the water branch of travel(); ordinal 1 would be lava
    @ModifyArg(method = "travel",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V",
                    ordinal = 0),
            index = 0)
    private float movement_plus$scaleSwimSpeed(float amount) {
        return EntityHooks.scaleSwimSpeed((LivingEntity) (Object) this, amount);
    }
}
