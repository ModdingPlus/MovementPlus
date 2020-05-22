package dev.necro.coyotelib.common.movement.midair_jump;

import dev.necro.coyotelib.common.network.BasePacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class MidairJumpPacket extends BasePacket<MidairJumpPacket> {

    public MidairJumpPacket() {super();}
    public MidairJumpPacket(PacketBuffer packetBuffer) {super(packetBuffer);}

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {}

    @Override
    public void writePacketData(PacketBuffer buf) {}

    @Override
    public void processPacket(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        PlayerEntity player = context.getSender();
        if (player != null){
            MidAirJumpHandler.playerJumpPacket(player);
        }
    }
}
