package moe.qbyte.movement_plus;

import dev.architectury.event.events.common.PlayerEvent;
import moe.qbyte.movement_plus.config.ConfigSyncPayload;
import moe.qbyte.movement_plus.jump_height.JumpHeightHandler;
import moe.qbyte.movement_plus.midair_jump.MidairJumpHandler;
import moe.qbyte.movement_plus.movement_speed.MovementSpeedHandler;
import moe.qbyte.movement_plus.network.ModNetwork;
import moe.qbyte.movement_plus.registry.ModAttributes;
import moe.qbyte.movement_plus.registry.ModSounds;
import moe.qbyte.movement_plus.step_height.StepHeightHandler;
import net.minecraft.resources.Identifier;

public final class MovementPlus {
    public static final String MOD_ID = "movement_plus";

    private MovementPlus() {}

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    /** Common entrypoint, called from both loader initializers. */
    public static void init() {
        ModAttributes.register();
        ModSounds.register();
        ModNetwork.register();

        MidairJumpHandler.init();
        StepHeightHandler.init();
        MovementSpeedHandler.init();
        JumpHeightHandler.init();

        // Sync the server config values to each client on join; needed for client-side jump prediction.
        PlayerEvent.PLAYER_JOIN.register(ConfigSyncPayload::sendTo);
    }
}
