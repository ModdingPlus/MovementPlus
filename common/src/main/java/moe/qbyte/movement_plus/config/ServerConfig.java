package moe.qbyte.movement_plus.config;

import moe.qbyte.movement_plus.MovementPlus;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Server-synced gameplay config. The spec is registered per loader (natively on NeoForge,
 * via Forge Config API Port on Fabric). The plain static fields below are what gameplay code
 * reads: on the server they are cached from the spec by the loader modules' config events,
 * on remote clients they are set by {@link ConfigSyncPayload} so client-side jump prediction
 * matches the server.
 */
public final class ServerConfig {
    public static final ModConfigSpec SERVER_SPEC;
    public static final ServerConfigTemplate SERVER;

    static {
        final Pair<ServerConfigTemplate, ModConfigSpec> specPair =
                new ModConfigSpec.Builder().configure(ServerConfigTemplate::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    public static int multiJumps = 0;
    public static int coyoteTime = 3;
    public static double stepHeightMultiplier = 1d;
    public static double stepHeightSneakingMultiplier = 1d;
    public static double jumpHeightBoost = 0d;
    public static double movementSpeedMultiplier = 1d;
    public static double swimSpeedMultiplier = 1d;

    private ServerConfig() {}

    /** Called from the loader modules whenever the config (re)loads. */
    public static void cache() {
        multiJumps = SERVER.multiJumps.get();
        coyoteTime = SERVER.coyoteTime.get();
        stepHeightMultiplier = SERVER.stepHeightMultiplier.get();
        stepHeightSneakingMultiplier = SERVER.stepHeightSneakingMultiplier.get();
        jumpHeightBoost = SERVER.jumpHeightBoost.get();
        movementSpeedMultiplier = SERVER.movementSpeedMultiplier.get();
        swimSpeedMultiplier = SERVER.swimSpeedMultiplier.get();
    }

    public static class ServerConfigTemplate {
        public final ModConfigSpec.IntValue multiJumps;
        public final ModConfigSpec.IntValue coyoteTime;
        public final ModConfigSpec.DoubleValue stepHeightMultiplier;
        public final ModConfigSpec.DoubleValue stepHeightSneakingMultiplier;
        public final ModConfigSpec.DoubleValue jumpHeightBoost;
        public final ModConfigSpec.DoubleValue movementSpeedMultiplier;
        public final ModConfigSpec.DoubleValue swimSpeedMultiplier;

        ServerConfigTemplate(ModConfigSpec.Builder builder) {
            builder.push("general");
            multiJumps = builder
                    .comment("The base amount of midair jumps every player has. This is summed with the amount of multi-jumps provided by equipment.")
                    .translation("text." + MovementPlus.MOD_ID + ".config.multiJumps")
                    .defineInRange("multiJumps", 0, 0, 1024);
            coyoteTime = builder
                    .comment("The base coyote time every player has in ticks. This is summed with the duration of coyote time provided by equipment.")
                    .translation("text." + MovementPlus.MOD_ID + ".config.coyoteTime")
                    .defineInRange("coyoteTime", 3, 0, 65536);
            stepHeightMultiplier = builder
                    .comment("Multiplier on the step height of every player. 1 leaves the step height attribute untouched.")
                    .translation("text." + MovementPlus.MOD_ID + ".config.stepHeightMultiplier")
                    .defineInRange("stepHeightMultiplier", 1.0d, 0d, 16d);
            stepHeightSneakingMultiplier = builder
                    .comment("Multiplier on the step height of every player while sneaking. It's highly recommended to keep this at 1.")
                    .translation("text." + MovementPlus.MOD_ID + ".config.stepHeightSneakingMultiplier")
                    .defineInRange("stepHeightSneakingMultiplier", 1.0d, 0d, 16d);
            jumpHeightBoost = builder
                    .comment("Additional base jump height in equivalent levels of the jump boost potion effect. Negative values possible.")
                    .translation("text." + MovementPlus.MOD_ID + ".config.jumpHeightBoost")
                    .defineInRange("jumpHeightBoost", 0.0d, -128d, 127d);
            movementSpeedMultiplier = builder
                    .comment("Multiplier on the base walking speed of every player.")
                    .translation("text." + MovementPlus.MOD_ID + ".config.movementSpeedMultiplier")
                    .defineInRange("movementSpeedMultiplier", 1.0d, 0d, 64d);
            swimSpeedMultiplier = builder
                    .comment("Multiplier on the swimming speed of every player. This is multiplied with the swim speed multiplier provided by equipment.")
                    .translation("text." + MovementPlus.MOD_ID + ".config.swimSpeedMultiplier")
                    .defineInRange("swimSpeedMultiplier", 1.0d, 0d, 64d);
            builder.pop();
        }
    }
}
