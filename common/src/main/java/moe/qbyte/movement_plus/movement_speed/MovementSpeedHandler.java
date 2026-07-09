package moe.qbyte.movement_plus.movement_speed;

import dev.architectury.event.events.common.TickEvent;
import moe.qbyte.movement_plus.config.ServerConfig;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

/** Applies the configured walking speed multiplier through the vanilla movement speed attribute. */
public final class MovementSpeedHandler {
    /** The attribute base value vanilla players are created with. */
    private static final double VANILLA_BASE_SPEED = 0.1d;

    private MovementSpeedHandler() {}

    public static void init() {
        TickEvent.PLAYER_PRE.register(player -> {
            AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (movementSpeed != null) {
                // setBaseValue is a no-op (no sync) when the value is unchanged; modifiers
                // from sprinting, potions and equipment apply on top of the base
                movementSpeed.setBaseValue(VANILLA_BASE_SPEED * ServerConfig.movementSpeedMultiplier);
            }
        });
    }
}
