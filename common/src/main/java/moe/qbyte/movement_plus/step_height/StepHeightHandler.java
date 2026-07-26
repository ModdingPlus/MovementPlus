package moe.qbyte.movement_plus.step_height;

import dev.architectury.event.events.common.TickEvent;
import moe.qbyte.movement_plus.MovementPlus;
import moe.qbyte.movement_plus.common.AttributeMultipliers;
import moe.qbyte.movement_plus.config.ServerConfig;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Applies the configured step height multiplier through a modifier on vanilla's
 * {@code step_height} attribute (new since 1.20.5; replaces the old per-tick
 * {@code maxUpStep} field write from 1.19.x).
 */
public final class StepHeightHandler {
    private static final Identifier MODIFIER_ID = MovementPlus.id("step_height_multiplier");

    private StepHeightHandler() {}

    public static void init() {
        TickEvent.PLAYER_PRE.register(player -> AttributeMultipliers.apply(
                player, Attributes.STEP_HEIGHT, MODIFIER_ID,
                player.isCrouching() ? ServerConfig.stepHeightSneakingMultiplier
                                     : ServerConfig.stepHeightMultiplier));
    }
}
