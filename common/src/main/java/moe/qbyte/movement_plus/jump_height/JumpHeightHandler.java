package moe.qbyte.movement_plus.jump_height;

import dev.architectury.event.events.common.TickEvent;
import moe.qbyte.movement_plus.MovementPlus;
import moe.qbyte.movement_plus.common.AttributeMultipliers;
import moe.qbyte.movement_plus.config.ServerConfig;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Applies the configured jump power multiplier through a modifier on vanilla's
 * {@code jump_strength} attribute (replaces the 1.19.x jump velocity hack),
 * with the gained apex height compensated on {@code safe_fall_distance}
 * the same way the jump boost potion effect does it.
 */
public final class JumpHeightHandler {
    private static final Identifier POWER_MODIFIER_ID = MovementPlus.id("jump_power_multiplier");
    private static final Identifier SAFE_FALL_MODIFIER_ID = MovementPlus.id("jump_power_safe_fall_bonus");

    /** Apex height of an unmodified vanilla jump (power 0.42) in blocks. */
    private static final double VANILLA_JUMP_HEIGHT = 1.25d;

    private JumpHeightHandler() {}

    public static void init() {
        TickEvent.PLAYER_PRE.register(player -> {
            double multiplier = ServerConfig.jumpPowerMultiplier;
            AttributeMultipliers.apply(player, Attributes.JUMP_STRENGTH, POWER_MODIFIER_ID, multiplier);
            AttributeMultipliers.applyFlat(player, Attributes.SAFE_FALL_DISTANCE, SAFE_FALL_MODIFIER_ID,
                    extraJumpHeight(multiplier));
        });
    }

    /**
     * Estimated extra apex height in blocks gained from the jump power multiplier;
     * jump height scales roughly quadratically with jump power. Never negative, so a
     * reduced jump power doesn't cause earlier fall damage.
     */
    public static double extraJumpHeight(double multiplier) {
        return Math.max(0d, VANILLA_JUMP_HEIGHT * (multiplier * multiplier - 1d));
    }
}
