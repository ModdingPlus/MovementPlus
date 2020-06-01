package dev.necro.coyotelib.api.common.movement.midair_jump;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class MidairJumpEvent extends PlayerEvent{
    public MidairJumpEvent(PlayerEntity player) {
        super(player);
    }

    public static class SetCoyoteTime extends MidairJumpEvent {

        private int coyoteTime;

        public SetCoyoteTime(PlayerEntity player, int initialValue) {
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

    @Cancelable
    public static class CoyoteTimeJump extends MidairJumpEvent {
        public CoyoteTimeJump(PlayerEntity player) {
            super(player);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    @Cancelable
    public static class SpecialJump extends MidairJumpEvent {
        private boolean jump;

        public SpecialJump(PlayerEntity player) {
            super(player);
        }

        public void setJump(boolean jump){
            this.jump = jump;
        }

        public boolean canJump(){
            return this.jump;
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    @Cancelable
    public static class SetMultiJumpCount extends MidairJumpEvent {
        private int jumps;

        public SetMultiJumpCount(PlayerEntity player) {
            super(player);
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

    @Cancelable
    public static class MultiJump extends MidairJumpEvent {
        public MultiJump(PlayerEntity player) {
            super(player);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }
}
