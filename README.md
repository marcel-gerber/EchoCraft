# EchoCraft

A very simple Minecraft server (only being able to respond to pings) built using **Netty**. This project demonstrates handling Minecraft packets from scratch.

## Project Structure

- **`netty/ChannelHandler`**: Manages incoming connections, reads and decodes packets, and maintains per-client state.
- **`packets/MinecraftPacket` Interface**: Defines the `encode` and `decode` methods for all packet types.
- **`packets/PacketRegistry`**: Handles the dynamic creation of packet objects based on their ID and the current server state.
- **`utils/Buffer`**: Provides helper methods for reading and writing variable-length integers and UTF-8 strings to/from a `ByteBuf`.

## How It Works

1. When a client connects, the server starts in the **Handshake** state.
2. Incoming packets are decoded based on the current connection state.
3. After processing the handshake, the server moves into the **Status** state.
4. The server can handle a ping request and respond with server info, such as max player count and version.

## How to Run
