package moe.qbyte.movement_plus.step_height;

import dev.architectury.event.events.common.TickEvent;
import moe.qbyte.movement_plus.config.ServerConfig;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Applies the configured step height through vanilla's {@code generic.step_height} attribute
 * (new since 1.20.5; replaces the old per-tick {@code maxUpStep} field write).
 */
public final class StepHeightHandler {
    private StepHeightHandler() {}

    public static void init() {
        TickEvent.PLAYER_PRE.register(player -> {
            AttributeInstance stepHeight = player.getAttribute(Attributes.STEP_HEIGHT);
            if (stepHeight != null) {
                // setBaseValue is a no-op (no sync) when the value is unchanged
                stepHeight.setBaseValue(player.isCrouching()
                        ? ServerConfig.stepHeightSneaking
                        : ServerConfig.stepHeight);
            }
        });
    }
}
