package moe.qbyte.movement_plus.client;

import dev.architectury.event.events.client.ClientTickEvent;
import moe.qbyte.movement_plus.midair_jump.MidairJumpHandler;
import net.minecraft.client.player.LocalPlayer;

/**
 * Detects fresh jump key presses on the client and attempts a (predicted) midair jump.
 * Replaces Forge's {@code MovementInputUpdateEvent}.
 */
public final class PlayerMovementInputHandler {
    private static boolean wasJumping = false;

    private PlayerMovementInputHandler() {}

    public static void init() {
        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            LocalPlayer player = minecraft.player;
            if (player == null) {
                wasJumping = false;
                return;
            }

            boolean jumping = player.input.jumping;
            if (!wasJumping && jumping) {
                MidairJumpHandler.attemptPlayerJump(player, true);
            }
            wasJumping = jumping;
        });
    }
}
