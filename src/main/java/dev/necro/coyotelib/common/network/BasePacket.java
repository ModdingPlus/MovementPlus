package dev.necro.coyotelib.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.function.Supplier;

public abstract class BasePacket<T> {
    private static Logger LOGGER = LogManager.getLogger();

    public BasePacket(){}

    public BasePacket(FriendlyByteBuf buf){
        try {
            this.readPacketData(buf);
        } catch (IOException e){
            LOGGER.error("Invalid packet received!");
            LOGGER.error(e);
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public abstract void readPacketData(FriendlyByteBuf buf) throws IOException;

    /**
     * Writes the raw packet data to the data stream.
     */
    public abstract void writePacketData(FriendlyByteBuf buf) throws IOException;

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public abstract void processPacket(Supplier<NetworkEvent.Context> ctx);
}
