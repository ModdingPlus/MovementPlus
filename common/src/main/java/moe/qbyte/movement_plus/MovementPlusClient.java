package moe.qbyte.movement_plus;

import moe.qbyte.movement_plus.client.PlayerMovementInputHandler;

public final class MovementPlusClient {
    private MovementPlusClient() {}

    /** Client entrypoint, called from both loader client initializers. */
    public static void init() {
        PlayerMovementInputHandler.init();
    }
}
