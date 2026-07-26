package moe.qbyte.movement_plus.movement_speed;

import dev.architectury.event.events.common.TickEvent;
import moe.qbyte.movement_plus.MovementPlus;
import moe.qbyte.movement_plus.common.AttributeMultipliers;
import moe.qbyte.movement_plus.config.ServerConfig;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Applies the configured walking speed multiplier through a modifier on the vanilla
 * movement speed attribute; multiplies after sprinting/potion/equipment modifiers.
 */
public final class MovementSpeedHandler {
    private static final Identifier MODIFIER_ID = MovementPlus.id("movement_speed_multiplier");

    private MovementSpeedHandler() {}

    public static void init() {
        TickEvent.PLAYER_PRE.register(player -> AttributeMultipliers.apply(
                player, Attributes.MOVEMENT_SPEED, MODIFIER_ID,
                ServerConfig.movementSpeedMultiplier));
    }
}
