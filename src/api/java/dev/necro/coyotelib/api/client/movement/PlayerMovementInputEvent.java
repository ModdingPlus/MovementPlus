package dev.necro.coyotelib.api.client.movement;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerMovementInputEvent extends PlayerEvent {

    private static boolean wasSneaking = false;
    private static boolean wasJumping = false;

    public PlayerMovementInputEvent(PlayerEntity player) {
        super(player);
    }

    public static class SneakInputEvent extends PlayerMovementInputEvent {

        public SneakInputEvent(PlayerEntity player) {
            super(player);
        }
    }

    public static class UnsneakInputEvent extends PlayerMovementInputEvent {
        public UnsneakInputEvent(PlayerEntity player) {
            super(player);
        }
    }

    public static class JumpInputEvent extends PlayerMovementInputEvent {
        public JumpInputEvent(PlayerEntity player) {
            super(player);
        }
    }
}
