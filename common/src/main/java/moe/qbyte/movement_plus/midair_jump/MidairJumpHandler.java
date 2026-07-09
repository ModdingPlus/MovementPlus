package moe.qbyte.movement_plus.midair_jump;

import dev.architectury.event.events.common.TickEvent;
import dev.architectury.networking.NetworkManager;
import moe.qbyte.movement_plus.api.MidairJumpEvents;
import moe.qbyte.movement_plus.config.ServerConfig;
import moe.qbyte.movement_plus.mixin.LivingEntityInvoker;
import moe.qbyte.movement_plus.registry.ModAttributes;
import moe.qbyte.movement_plus.registry.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public final class MidairJumpHandler {
    private MidairJumpHandler() {}

    public static void init() {
        TickEvent.PLAYER_POST.register(MidairJumpHandler::playerTick);
    }

    /**
     * Any state in which the player has "solid footing": midair jumps replenish and
     * midair jumping is not possible.
     */
    public static boolean hasFooting(Player player) {
        return player.onGround()
                || player.isSwimming()
                || player.isVisuallySwimming()
                || player.isVisuallyCrawling()
                || player.isUnderWater()
                || player.isFallFlying()
                || player.isSleeping();
    }

    /** Track time off ground for coyote time and reset midair jumps while the player has footing. */
    private static void playerTick(Player player) {
        if (!player.isAlive()) return;

        MidairJumpState state = MidairJumpState.of(player);
        if (hasFooting(player)) {
            state.movement_plus$setTimeOffGround(0);
            state.movement_plus$setJumped(false);

            MidairJumpEvents.RESET.invoker().onReset(player);
            if (state.movement_plus$getUsedJumps() != 0) {
                state.movement_plus$setUsedJumps(0);
            }
        } else {
            state.movement_plus$setTimeOffGround(state.movement_plus$getTimeOffGround() + 1);
        }
    }

    /** Called from LivingEntityMixin when a player jumps, to lock out the coyote jump until landing. */
    public static void onJump(Player player) {
        MidairJumpState.of(player).movement_plus$setJumped(true);
    }

    /**
     * Attempt a midair jump: coyote time first, then multi-jumps, then mod-provided special jumps.
     * Called client-side on jump key press (predicted) and server-side from {@link MidairJumpPayload}.
     */
    public static void attemptPlayerJump(Player player, boolean clientSide) {
        if (!player.isAlive() || hasFooting(player)) return;

        MidairJumpState state = MidairJumpState.of(player);

        if (player.getDeltaMovement().y < 0 && !state.movement_plus$hasJumped()) {
            int coyoteTime = (int) Math.floor(
                    player.getAttributeValue(ModAttributes.COYOTE_TIME.holder()) + ServerConfig.coyoteTime);

            if (coyoteTime > 0 && state.movement_plus$getTimeOffGround() <= coyoteTime) {
                if (!MidairJumpEvents.COYOTE_TIME_PRE.invoker().beforeJump(player).isFalse()) {
                    performJump(player, clientSide);
                    MidairJumpEvents.COYOTE_TIME_POST.invoker().afterJump(player);
                    return;
                }
            }
        }

        int multiJumps = (int) Math.floor(
                player.getAttributeValue(ModAttributes.MULTI_JUMPS.holder()) + ServerConfig.multiJumps);
        int usedJumps = state.movement_plus$getUsedJumps();

        if (multiJumps > 0 && usedJumps < multiJumps) {
            if (!MidairJumpEvents.MULTI_JUMP_PRE.invoker().beforeJump(player).isFalse()) {
                state.movement_plus$setUsedJumps(usedJumps + 1);
                performJump(player, clientSide);

                MidairJumpEvents.MultiJumpContext context = new MidairJumpEvents.MultiJumpContext();
                MidairJumpEvents.MULTI_JUMP_POST.invoker().afterJump(player, context);
                if (context.shouldPlayEffects()) playMultiJumpEffects(player);
                return;
            }
        }

        MidairJumpEvents.SpecialJumpContext context = new MidairJumpEvents.SpecialJumpContext();
        if (!MidairJumpEvents.SPECIAL_JUMP.invoker().onSpecialJump(player, context).isFalse() && context.canJump()) {
            performJump(player, clientSide);
            if (context.hasCallback()) context.getCallback().accept(player);
        }
    }

    private static void performJump(Player player, boolean clientSide) {
        if (clientSide) NetworkManager.sendToServer(MidairJumpPayload.INSTANCE);
        ((LivingEntityInvoker) player).movement_plus$jumpFromGround();
    }

    private static void playMultiJumpEffects(Player player) {
        player.playSound(ModSounds.MIDAIR_JUMP.get(), .6f, 1.8f);

        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CLOUD,
                    player.getX(), player.getY(), player.getZ(), 1, 0, 0, 0, 0);
        }
    }
}
