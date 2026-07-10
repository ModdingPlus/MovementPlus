package moe.qbyte.movement_plus;

import moe.qbyte.movement_plus.config.ServerConfig;
import moe.qbyte.movement_plus.jump_height.JumpHeightHandler;
import moe.qbyte.movement_plus.midair_jump.MidairJumpHandler;
import moe.qbyte.movement_plus.midair_jump.MidairJumpState;
import moe.qbyte.movement_plus.registry.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/** Entry points called from the mixins; replaces the Forge LivingJump/LivingFall event listeners. */
public final class EntityHooks {
    private EntityHooks() {}

    /** After a living entity jumped ({@code jumpFromGround} TAIL). */
    public static void onLivingJump(LivingEntity entity) {
        if (!(entity instanceof Player player)) return;
        MidairJumpHandler.onJump(player);
    }

    /**
     * Scales the water-movement acceleration in {@code travel} for players;
     * cross-loader replacement for NeoForge's {@code swim_speed} attribute.
     */
    public static float scaleSwimSpeed(LivingEntity entity, float amount) {
        if (!(entity instanceof Player player)) return amount;
        return (float) (amount
                * player.getAttributeValue(ModAttributes.SWIM_SPEED.holder())
                * ServerConfig.swimSpeedMultiplier);
    }

    /**
     * Adjusts the incoming fall distance in {@code causeFallDamage} for players.
     * The jump power compensation lives on the safe fall distance attribute; this only
     * covers midair jumps used since the last landing, which soften the fall like in 1.19.x.
     */
    public static float modifyFallDistance(LivingEntity entity, float distance) {
        if (!(entity instanceof Player player)) return distance;

        if (MidairJumpState.of(player).movement_plus$getUsedJumps() > 0) {
            double extraJumpHeight = JumpHeightHandler.extraJumpHeight(ServerConfig.jumpPowerMultiplier);
            distance = (float) Math.max(0d, distance - extraJumpHeight / 1.5d - 1d);
        }
        return distance;
    }
}
