package moe.qbit.movement_plus.common.midair_jump;

import moe.qbit.movement_plus.common.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MidairJumpPacket extends BasePacket<MidairJumpPacket> {

    public MidairJumpPacket() {super();}
    public MidairJumpPacket(FriendlyByteBuf packetBuffer) {super(packetBuffer);}

    @Override
    public void readPacketData(FriendlyByteBuf buf) {}

    @Override
    public void writePacketData(FriendlyByteBuf buf) {}

    @Override
    public void processPacket(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        Player player = context.getSender();
        if (player != null){
            context.enqueueWork(()-> MidairJumpHandler.playerJumpPacket(player));
        }
        context.setPacketHandled(true);
    }
}
