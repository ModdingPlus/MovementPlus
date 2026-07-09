package moe.qbyte.movement_plus.mixin;

import moe.qbyte.movement_plus.midair_jump.MidairJumpState;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * Holds the transient midair jump state. The custom attributes are attached per loader:
 * via {@code FabricDefaultAttributeRegistry} on Fabric and
 * {@code EntityAttributeModificationEvent} on NeoForge, since default attribute suppliers
 * may be built before mod init on Fabric (dev-time {@code Bootstrap.validate()}).
 */
@Mixin(Player.class)
public abstract class PlayerMixin implements MidairJumpState {

    @Unique
    private int movement_plus$usedJumps;
    @Unique
    private int movement_plus$timeOffGround;
    @Unique
    private boolean movement_plus$jumped;

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
