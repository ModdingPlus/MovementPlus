package moe.qbyte.movement_plus.mixin;

import moe.qbyte.movement_plus.midair_jump.MidairJumpState;
import moe.qbyte.movement_plus.registry.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin implements MidairJumpState {

    @Unique
    private int movement_plus$usedJumps;
    @Unique
    private int movement_plus$timeOffGround;
    @Unique
    private boolean movement_plus$jumped;

    // replaces Forge's EntityAttributeModificationEvent
    @Inject(method = "createAttributes", at = @At("RETURN"))
    private static void movement_plus$addAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue()
                .add(ModAttributes.MULTI_JUMPS.holder())
                .add(ModAttributes.COYOTE_TIME.holder());
    }

    @Override
    public int movement_plus$getUsedJumps() {
        return this.movement_plus$usedJumps;
    }

    @Override
    public void movement_plus$setUsedJumps(int jumps) {
        this.movement_plus$usedJumps = jumps;
    }

    @Override
    public int movement_plus$getTimeOffGround() {
        return this.movement_plus$timeOffGround;
    }

    @Override
    public void movement_plus$setTimeOffGround(int ticks) {
        this.movement_plus$timeOffGround = ticks;
    }

    @Override
    public boolean movement_plus$hasJumped() {
        return this.movement_plus$jumped;
    }

    @Override
    public void movement_plus$setJumped(boolean jumped) {
        this.movement_plus$jumped = jumped;
    }
}
