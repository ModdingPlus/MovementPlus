package moe.qbit.movement_plus.common.network;

import moe.qbit.movement_plus.MovementPlus;
import moe.qbit.movement_plus.common.midair_jump.MidairJumpPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = MovementPlus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MovementPlus.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    @SubscribeEvent
    @SuppressWarnings("UnusedAssignment")
    public static void setup(final FMLCommonSetupEvent event) {
        int id=0;
        INSTANCE.registerMessage(id++,
                MidairJumpPacket.class,
                MidairJumpPacket::writePacketData,
                MidairJumpPacket::new,
                MidairJumpPacket::processPacket,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

}
