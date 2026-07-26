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
    private static boolean hadFooting = true;

    private PlayerMovementInputHandler() {}

    public static void init() {
        // The footing check must use the state from before the tick: a fresh press on the
        // ground performs the vanilla jump during the tick, and by tick end the player already
        // looks airborne, which would stack a midair jump onto the same press.
        ClientTickEvent.CLIENT_PRE.register(minecraft -> {
            LocalPlayer player = minecraft.player;
            hadFooting = player == null || MidairJumpHandler.hasFooting(player);
        });

        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            LocalPlayer player = minecraft.player;
            if (player == null) {
                wasJumping = false;
                return;
            }

            boolean jumping = player.input.keyPresses.jump();
            if (!wasJumping && jumping && !hadFooting) {
                MidairJumpHandler.attemptPlayerJump(player, true);
            }
            wasJumping = jumping;
        });
    }
}
