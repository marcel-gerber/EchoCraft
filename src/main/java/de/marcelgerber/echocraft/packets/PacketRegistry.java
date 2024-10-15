package de.marcelgerber.echocraft.packets;

import de.marcelgerber.echocraft.utils.State;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for identifying packets based on their ID
 */
public class PacketRegistry {

    // All packets will be saved in this Map indexed with its packetID
    private static final Map<Integer, Class<? extends MinecraftPacket>> handshakingPackets = new HashMap<>();
    private static final Map<Integer, Class<? extends MinecraftPacket>> statusPackets = new HashMap<>();

    static {
        // Register Handshake packet
        handshakingPackets.put(0x00, HandshakePacket.class);

        // Register Status packets
        statusPackets.put(0x00, StatusPacket.class);
        statusPackets.put(0x01, PingPacket.class);
    }

    /**
     * Creates a packet based on the State and packetID
     *
     * @param state
     * @param packetID
     * @return MinecraftPacket
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static MinecraftPacket createPacket(State state, final int packetID) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Class<? extends MinecraftPacket> clazz = null;
        switch (state) {
            case HANDSHAKING -> clazz = handshakingPackets.get(packetID);
            case STATUS -> clazz = statusPackets.get(packetID);
        }

        if(clazz == null) {
            return null;
        }
        return clazz.getDeclaredConstructor().newInstance();
    }

}
