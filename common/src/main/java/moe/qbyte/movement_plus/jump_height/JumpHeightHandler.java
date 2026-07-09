package moe.qbyte.movement_plus.jump_height;

import moe.qbyte.movement_plus.config.ServerConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Applies the configured flat jump height boost. Jump height from equipment/effects is covered
 * by vanilla's {@code generic.jump_strength} attribute since 1.20.5, so unlike 1.19.x no mixin
 * into {@code getJumpPower()} is needed anymore.
 */
public final class JumpHeightHandler {
    private JumpHeightHandler() {}

    /** Called from LivingEntityMixin after a player jumped. */
    public static void onJump(Player player) {
        if (ServerConfig.jumpHeightBoost == 0.0d) return;
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x,
                Math.max(0, motion.y + (0.1d * ServerConfig.jumpHeightBoost)), motion.z);
    }

    /** Fall distance reduction matching the extra height gained from the jump boost. */
    public static float modifyFallDistance(Player player, float distance) {
        return (float) Math.max(0d, distance - Math.max(ServerConfig.jumpHeightBoost, 0d));
    }
}
