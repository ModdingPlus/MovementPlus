package moe.qbyte.movement_plus.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import moe.qbyte.movement_plus.config.ConfigSyncPayload;
import moe.qbyte.movement_plus.midair_jump.MidairJumpHandler;
import moe.qbyte.movement_plus.midair_jump.MidairJumpPayload;
import net.minecraft.server.level.ServerPlayer;

public final class ModNetwork {
    private ModNetwork() {}

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S,
                MidairJumpPayload.TYPE, MidairJumpPayload.STREAM_CODEC,
                (payload, context) -> context.queue(() -> {
                    if (context.getPlayer() instanceof ServerPlayer player) {
                        MidairJumpHandler.attemptPlayerJump(player, false);
                    }
                }));

        if (Platform.getEnvironment() == Env.CLIENT) {
            NetworkManager.registerReceiver(NetworkManager.Side.S2C,
                    ConfigSyncPayload.TYPE, ConfigSyncPayload.STREAM_CODEC,
                    (payload, context) -> context.queue(payload::apply));
        } else {
            // dedicated servers cannot register S2C receivers but must still know the payload type
            NetworkManager.registerS2CPayloadType(ConfigSyncPayload.TYPE, ConfigSyncPayload.STREAM_CODEC);
        }
    }
}
