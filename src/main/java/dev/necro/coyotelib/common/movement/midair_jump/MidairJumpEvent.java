package dev.necro.coyotelib.common.movement.midair_jump;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class MidairJumpEvent extends PlayerEvent{
    public MidairJumpEvent(Player player) {
        super(player);
    }

    public static class SetCoyoteTime extends MidairJumpEvent {

        private int coyoteTime;

        public SetCoyoteTime(Player player) {
            this(player, 0);
        }


        public SetCoyoteTime(Player player, int initialValue) {
            super(player);
            this.coyoteTime = initialValue;
        }

        public void addCoyoteTime(int ticks) {
            this.coyoteTime += ticks;
        }

        public int getCoyoteTime() {
            return coyoteTime;
        }

        public void setCoyoteTime(int ticks) {
            this.coyoteTime = ticks;
        }

        public void maximizeCoyoteTime(int ticks) {
            this.coyoteTime = Math.max(this.coyoteTime, ticks);
        }
    }

    public static abstract class CoyoteTimeJump extends MidairJumpEvent {
        public CoyoteTimeJump(Player player) {
            super(player);
        }

        @Cancelable
        public static class Pre extends SpecialJump {
            private boolean jump;

            public Pre(Player player) {
                super(player);
            }

            @Override
            public boolean isCancelable() {
                return true;
            }
        }

        public static class Post extends SpecialJump {
            private boolean _isHandled;

            public Post(Player player) {
                super(player);
            }

            /**
             * Whether or not the even has already been handled cosmetically,
             * meaning primarily applying cosmetic effects like particles and sounds
             * @return whether the event has been handled
             */
            public boolean isHandled(){ return this._isHandled; }

            /**
             * Indicate that the event has been handled cosmetically
             */
            public void setHandled(){ this._isHandled = true; }
        }
    }

    public static class SpecialJump extends MidairJumpEvent {
        private boolean _jump;
        private Consumer<Player> _callback;

        public SpecialJump(Player player) {
            super(player);
        }

        public void setJump(boolean jump){
            this._jump = jump;
        }

        public boolean canJump(){
            return this._jump;
        }

        public void setCallback(Consumer<Player> callback){
            this._callback = callback;
        }

        public boolean hasCallback(){
            return this._callback != null;
        }

        @Nullable
        public Consumer<Player> getCallback(){
            return this._callback;
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    @Cancelable
    public static class SetMultiJumpCount extends MidairJumpEvent {
        private int jumps;

        public SetMultiJumpCount(Player player) {
            this(player, 0);
        }

        public SetMultiJumpCount(Player player, int initialValue) {
            super(player);
            this.jumps = initialValue;
        }

        public int addJumps(int count) {
            return jumps += count;
        }

        public int getJumps() {
            return jumps;
        }

        public void setJumps(int count) {
            this.jumps = count;
        }

        public void maximizeJumps(int count) {
            this.jumps = Math.max(this.jumps, count);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    public static abstract class MultiJump extends MidairJumpEvent {
        public MultiJump(Player player) {
            super(player);
        }

        @Cancelable
        public static class Pre extends MultiJump {
            public Pre(Player player) {
                super(player);
            }

            @Override
            public boolean isCancelable() {
                return true;
            }
        }

        public static class Post extends MultiJump {
            private boolean _isHandled;

            public Post(Player player) {
                super(player);
            }

            /**
             * Whether or not the even has already been handled cosmetically,
             * meaning primarily applying cosmetic effects like particles and sounds
             * @return whether the event has been handled
             */
            public boolean isHandled(){ return this._isHandled; }

            /**
             * Indicate that the event has been handled cosmetically
             */
            public void setHandled(){ this._isHandled = true; }
        }
    }

    /**
     * Fired whenever a players midair jump count would reset, usually when the player is on the ground.
     */
    public static class Reset extends MidairJumpEvent {
        public Reset(Player player) {
            super(player);
        }
    }
}
