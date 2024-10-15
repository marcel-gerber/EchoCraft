package de.marcelgerber.echocraft.packets;

import de.marcelgerber.echocraft.utils.Buffer;
import io.netty.buffer.ByteBuf;

/**
 * Represents a Ping Request and Pong Response packet
 */
public class PingPacket implements MinecraftPacket {

    private long payload;

    public PingPacket() {}

    public PingPacket(long payload) {
        this.payload = payload;
    }

    /**
     * Encodes the Pong Response packet
     *
     * @param byteBuf
     */
    @Override
    public void encode(ByteBuf byteBuf) {
        // Length of the packet. 8 bytes (payload) + 1 byte (packetID)
        Buffer.writeVarInt(byteBuf, 9);

        // PacketID: 0x01
        Buffer.writeVarInt(byteBuf, 1);

        byteBuf.writeLong(payload);
    }

    /**
     * Decodes the Ping Request packet
     *
     * @param byteBuf
     */
    @Override
    public void decode(ByteBuf byteBuf) {
        payload = byteBuf.readLong();
    }

    public long getPayload() {
        return payload;
    }
}
