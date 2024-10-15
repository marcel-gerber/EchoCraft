package de.marcelgerber.echocraft.packets;

import de.marcelgerber.echocraft.utils.Buffer;
import io.netty.buffer.ByteBuf;

public class StatusPacket implements MinecraftPacket {

    /**
     * Encodes Status Response packet
     *
     * @param byteBuf
     */
    @Override
    public void encode(ByteBuf byteBuf) {
        String response = "{\"description\":{\"extra\":[{\"text\":\"Bauserver\"}],\"text\":\"\"},\"players\":{\"max\":20,\"online\":0},\"version\":{\"name\":\"Spigot 1.21.1\",\"protocol\":767}}";

        // Length of PacketID + Data
        Buffer.writeVarInt(byteBuf, response.length() + 3);

        // PacketID = 0
        Buffer.writeVarInt(byteBuf, 0);

        // Write ServerInfo string to byteBuf
        Buffer.writeString(byteBuf, response, Short.MAX_VALUE);
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
