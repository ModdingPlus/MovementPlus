package moe.qbyte.movement_plus;

import moe.qbyte.movement_plus.jump_height.JumpHeightHandler;
import moe.qbyte.movement_plus.midair_jump.MidairJumpHandler;
import moe.qbyte.movement_plus.midair_jump.MidairJumpState;
import moe.qbyte.movement_plus.config.ServerConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/** Entry points called from the mixins; replaces the Forge LivingJump/LivingFall event listeners. */
public final class EntityHooks {
    private EntityHooks() {}

    /** After a living entity jumped ({@code jumpFromGround} TAIL). */
    public static void onLivingJump(LivingEntity entity) {
        if (!(entity instanceof Player player)) return;
        MidairJumpHandler.onJump(player);
        JumpHeightHandler.onJump(player);
    }

    /** Adjusts the incoming fall distance in {@code causeFallDamage} for players. */
    public static float modifyFallDistance(LivingEntity entity, float distance) {
        if (!(entity instanceof Player player)) return distance;

        distance = JumpHeightHandler.modifyFallDistance(player, distance);

        // Midair jumps used since last landing soften the fall like in 1.19.x.
        if (MidairJumpState.of(player).movement_plus$getUsedJumps() > 0) {
            distance = (float) Math.max(0d, distance - ServerConfig.jumpHeightBoost / 1.5d - 1d);
        }
        return distance;
    }
}
