package moe.qbyte.movement_plus.api;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Events around midair jumping (coyote time, multi-jumps, and mod-provided special jumps).
 * These fire on both logical sides; check {@code player.level().isClientSide()} if it matters.
 */
public final class MidairJumpEvents {
    private MidairJumpEvents() {}

    /** Fired before a coyote-time jump is performed. Return {@code EventResult.interruptFalse()} to cancel it. */
    public static final Event<Pre> COYOTE_TIME_PRE = EventFactory.createEventResult();
    /** Fired after a coyote-time jump was performed. */
    public static final Event<Post> COYOTE_TIME_POST = EventFactory.createLoop();

    /** Fired before a multi-jump is performed. Return {@code EventResult.interruptFalse()} to cancel it. */
    public static final Event<Pre> MULTI_JUMP_PRE = EventFactory.createEventResult();
    /** Fired after a multi-jump was performed; allows suppressing the default sound/particle effects. */
    public static final Event<MultiJumpPost> MULTI_JUMP_POST = EventFactory.createLoop();

    /**
     * Fired when neither coyote time nor multi-jumps allowed a jump; other mods may grant a jump
     * through the context. Return {@code EventResult.interruptFalse()} to prevent any jump.
     */
    public static final Event<SpecialJump> SPECIAL_JUMP = EventFactory.createEventResult();

    /** Fired whenever a player's midair jump count would reset, usually when the player is on the ground. */
    public static final Event<Reset> RESET = EventFactory.createLoop();

    @FunctionalInterface
    public interface Pre {
        EventResult beforeJump(Player player);
    }

    @FunctionalInterface
    public interface Post {
        void afterJump(Player player);
    }

    @FunctionalInterface
    public interface MultiJumpPost {
        void afterJump(Player player, MultiJumpContext context);
    }

    @FunctionalInterface
    public interface SpecialJump {
        EventResult onSpecialJump(Player player, SpecialJumpContext context);
    }

    @FunctionalInterface
    public interface Reset {
        void onReset(Player player);
    }

    /** Mutable context for {@link #MULTI_JUMP_POST}. */
    public static final class MultiJumpContext {
        private boolean playEffects = true;

        /**
         * Whether the jump still needs to be handled cosmetically.
         * If this is false no sounds or particles need to be emitted.
         */
        public boolean shouldPlayEffects() {
            return this.playEffects;
        }

        /**
         * Indicate that the jump has been handled cosmetically and as such
         * no sounds or particles need to be emitted.
         */
        public void preventPlayingEffects() {
            this.playEffects = false;
        }
    }

    /** Mutable context for {@link #SPECIAL_JUMP}. */
    public static final class SpecialJumpContext {
        private boolean jump;
        private @Nullable Consumer<Player> callback;

        public void setJump(boolean jump) {
            this.jump = jump;
        }

        public boolean canJump() {
            return this.jump;
        }

        /** Optional callback invoked after the granted jump was performed. */
        public void setCallback(@Nullable Consumer<Player> callback) {
            this.callback = callback;
        }

        public boolean hasCallback() {
            return this.callback != null;
        }

        public @Nullable Consumer<Player> getCallback() {
            return this.callback;
        }
    }
}
