package moe.qbyte.movement_plus.config;

import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.GameInstance;
import moe.qbyte.movement_plus.MovementPlus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

/**
 * S2C payload carrying the server's gameplay config values. Sent on join and on config reload,
 * so the client can predict midair jumps with the same values the server validates with.
 */
public record ConfigSyncPayload(int multiJumps, int coyoteTime, double stepHeightMultiplier,
                                double stepHeightSneakingMultiplier, double jumpHeightBoost,
                                double movementSpeedMultiplier, double swimSpeedMultiplier)
        implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ConfigSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(MovementPlus.id("config_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConfigSyncPayload> STREAM_CODEC = StreamCodec.of(
            (buf, payload) -> {
                buf.writeVarInt(payload.multiJumps);
                buf.writeVarInt(payload.coyoteTime);
                buf.writeDouble(payload.stepHeightMultiplier);
                buf.writeDouble(payload.stepHeightSneakingMultiplier);
                buf.writeDouble(payload.jumpHeightBoost);
                buf.writeDouble(payload.movementSpeedMultiplier);
                buf.writeDouble(payload.swimSpeedMultiplier);
            },
            buf -> new ConfigSyncPayload(buf.readVarInt(), buf.readVarInt(),
                    buf.readDouble(), buf.readDouble(), buf.readDouble(),
                    buf.readDouble(), buf.readDouble()));

    public static ConfigSyncPayload fromCurrentValues() {
        return new ConfigSyncPayload(ServerConfig.multiJumps, ServerConfig.coyoteTime,
                ServerConfig.stepHeightMultiplier, ServerConfig.stepHeightSneakingMultiplier,
                ServerConfig.jumpHeightBoost,
                ServerConfig.movementSpeedMultiplier, ServerConfig.swimSpeedMultiplier);
    }

    public static void sendTo(ServerPlayer player) {
        NetworkManager.sendToPlayer(player, fromCurrentValues());
    }

    /** Re-sync after a config reload; no-op when no server is running. */
    public static void syncToAll() {
        MinecraftServer server = GameInstance.getServer();
        if (server == null) return;
        ConfigSyncPayload payload = fromCurrentValues();
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            NetworkManager.sendToPlayer(player, payload);
        }
    }

    /** Applied on the client. */
    public void apply() {
        ServerConfig.multiJumps = this.multiJumps;
        ServerConfig.coyoteTime = this.coyoteTime;
        ServerConfig.stepHeightMultiplier = this.stepHeightMultiplier;
        ServerConfig.stepHeightSneakingMultiplier = this.stepHeightSneakingMultiplier;
        ServerConfig.jumpHeightBoost = this.jumpHeightBoost;
        ServerConfig.movementSpeedMultiplier = this.movementSpeedMultiplier;
        ServerConfig.swimSpeedMultiplier = this.swimSpeedMultiplier;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
