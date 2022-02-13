package moe.qbit.movement_plus.api.client;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerMovementInputEvent extends PlayerEvent {

    public PlayerMovementInputEvent(Player player) {
        super(player);
    }

    public static class SneakInputEvent extends PlayerMovementInputEvent {

        public SneakInputEvent(Player player) {
            super(player);
        }
    }

    public static class UnsneakInputEvent extends PlayerMovementInputEvent {
        public UnsneakInputEvent(Player player) {
            super(player);
        }
    }

    public static class JumpInputEvent extends PlayerMovementInputEvent {
        public JumpInputEvent(Player player) {
            super(player);
        }
    }
}
