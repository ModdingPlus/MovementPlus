package moe.qbyte.movement_plus.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/** {@code jumpFromGround} is protected in Mojang mappings; this replaces the old Forge AT access. */
@Mixin(LivingEntity.class)
public interface LivingEntityInvoker {
    @Invoker("jumpFromGround")
    void movement_plus$jumpFromGround();
}
