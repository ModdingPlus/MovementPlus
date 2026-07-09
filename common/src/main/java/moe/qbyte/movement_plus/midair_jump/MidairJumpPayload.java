package moe.qbyte.movement_plus.midair_jump;

import moe.qbyte.movement_plus.MovementPlus;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/** C2S payload: the client performed a (predicted) midair jump; the server validates and mirrors it. */
public record MidairJumpPayload() implements CustomPacketPayload {

    public static final MidairJumpPayload INSTANCE = new MidairJumpPayload();

    public static final CustomPacketPayload.Type<MidairJumpPayload> TYPE =
            new CustomPacketPayload.Type<>(MovementPlus.id("midair_jump"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MidairJumpPayload> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
