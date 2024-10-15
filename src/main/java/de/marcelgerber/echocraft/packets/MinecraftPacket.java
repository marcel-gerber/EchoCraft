package de.marcelgerber.echocraft.packets;

import io.netty.buffer.ByteBuf;

/**
 * Interface representing a Minecraft packet
 */
public interface MinecraftPacket {

    void encode(ByteBuf byteBuf);
    void decode(ByteBuf byteBuf);

}
