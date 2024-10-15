package de.marcelgerber.echocraft.netty;

import de.marcelgerber.echocraft.packets.*;
import de.marcelgerber.echocraft.utils.Buffer;
import de.marcelgerber.echocraft.utils.State;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * This class handles incoming data (packets). Each player/connection has their own instance of 'ChannelHandler'
 */
public class ChannelHandler extends ChannelInboundHandlerAdapter {

    // We have to know in which state the player/connection currently is
    private State currentState = State.HANDSHAKING;

    /**
     * Method for handling incoming data
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        int length = Buffer.readVarInt(byteBuf);
        int packetID = Buffer.readVarInt(byteBuf);

        // Get the Minecraft Packet based on the packets' ID
        MinecraftPacket minecraftPacket = PacketRegistry.createPacket(currentState, packetID);

        if(minecraftPacket == null) {
            System.err.println("Packet ID " + packetID + " not found");
            return;
        }

        // Decode the packet
        minecraftPacket.decode(byteBuf);

        switch (minecraftPacket) {
            case HandshakePacket ignored -> {
                // Update the currentState when we receive a Handshake
                currentState = State.STATUS;

                // Respond with Status Response packet
                StatusPacket statusPacket = new StatusPacket();

                // Allocate a new ByteBuf
                ByteBuf responseBuffer = ctx.alloc().buffer();

                // Encode the packet and send it to the client
                statusPacket.encode(responseBuffer);
                ctx.writeAndFlush(responseBuffer);
            }
            case PingPacket pingPacketReceived -> {
                // Respond with Ping Response packet
                PingPacket pingPacket = new PingPacket(pingPacketReceived.getPayload());

                // Allocate a new ByteBuf
                ByteBuf responseBuffer = ctx.alloc().buffer();

                // Encode the packet and send it to the client
                pingPacket.encode(responseBuffer);
                ctx.writeAndFlush(responseBuffer);
            }
            default -> {
            }
        }

    }
}
