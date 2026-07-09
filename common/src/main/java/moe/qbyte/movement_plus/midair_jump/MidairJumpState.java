package moe.qbyte.movement_plus.midair_jump;

import net.minecraft.world.entity.player.Player;

/**
 * Per-player midair jump state, implemented onto {@code Player} by {@code PlayerMixin}.
 * Replaces the Forge {@code getPersistentData()} NBT from 1.19.x; the state is transient,
 * which is fine since it resets whenever the player touches ground anyway.
 */
public interface MidairJumpState {
    static MidairJumpState of(Player player) {
        return (MidairJumpState) player;
    }

    int movement_plus$getUsedJumps();

    void movement_plus$setUsedJumps(int jumps);

    int movement_plus$getTimeOffGround();

    void movement_plus$setTimeOffGround(int ticks);

    boolean movement_plus$hasJumped();

    void movement_plus$setJumped(boolean jumped);
}
