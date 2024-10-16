package de.marcelgerber.echocraft.packets;

import de.marcelgerber.echocraft.utils.Buffer;
import de.marcelgerber.echocraft.utils.ServerInfo;
import io.netty.buffer.ByteBuf;

public class StatusPacket implements MinecraftPacket {

    /**
     * Encodes Status Response packet
     *
     * @param byteBuf
     */
    @Override
    public void encode(ByteBuf byteBuf) {
        // Respond with information of the server
        String serverStatus = ServerInfo.getServerStatusJson();

        // Length of PacketID + Data
        Buffer.writeVarInt(byteBuf, serverStatus.length() + 3);

        // PacketID = 0x00
        Buffer.writeVarInt(byteBuf, 0);

        // Write ServerInfo string to byteBuf
        Buffer.writeString(byteBuf, serverStatus, Short.MAX_VALUE);
    }

    /**
     * Decodes Status Request packet. This packet has no fields
     *
     * @param byteBuf
     */
    @Override
    public void decode(ByteBuf byteBuf) {

    }
}
