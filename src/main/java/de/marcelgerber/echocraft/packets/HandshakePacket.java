package de.marcelgerber.echocraft.packets;

import de.marcelgerber.echocraft.utils.Buffer;
import io.netty.buffer.ByteBuf;

public class HandshakePacket implements MinecraftPacket {

    /**
     * There are no client bound packets in the Handshaking state
     *
     * @param byteBuf
     */
    @Override
    public void encode(ByteBuf byteBuf) {

    }

    /**
     * Decodes the Handshake packet
     *
     * @param byteBuf
     */
    @Override
    public void decode(ByteBuf byteBuf) {
        // VarInt: protocol Version (767 = 1.21.1)
        int protocolVersion = Buffer.readVarInt(byteBuf);

        // String: Server address ("localhost")
        String host = Buffer.readString(byteBuf, 255);

        // Unsigned Short: Port (25565)
        int port = byteBuf.readUnsignedShort();

        // VarInt: next state (1 = Status)
        int nextState = Buffer.readVarInt(byteBuf);

        // The client sends the Status Request packet together with the handshake packet
        int length = Buffer.readVarInt(byteBuf);
        int packetID = Buffer.readVarInt(byteBuf);
    }
}
