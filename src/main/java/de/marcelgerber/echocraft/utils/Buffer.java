package de.marcelgerber.echocraft.utils;

import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;

/**
 * Class providing helper functions for reading and writing to and from a ByteBuf
 */
public class Buffer {

    /**
     * Reads a variable-length integer from the ByteBuf and returns it
     *
     * @param byteBuf
     * @return int
     */
    public static int readVarInt(ByteBuf byteBuf) {
        int value = 0;
        int bytes = 0;
        byte currentByte;

        while(true) {
            currentByte = byteBuf.readByte();
            value |= (currentByte & 0x7F) << (bytes++ * 7);

            if(bytes > 5) {
                throw new RuntimeException("VarInt too big");
            }

            if((currentByte & 0x80) != 0x80) break;
        }
        return value;
    }

    public static void writeVarInt(ByteBuf byteBuf, int value) {
        int part;
        while(true) {
            part = value & 0x7F;

            value >>>= 7;
            if(value != 0) {
                part |= 0x80;
            }
            byteBuf.writeByte(part);

            if(value == 0) break;
        }
    }

    /**
     * Writes a variable-length integer to the ByteBuf
     *
     * @param byteBuf
     * @param maxLength
     * @return
     */
    public static String readString(ByteBuf byteBuf, int maxLength) {
        int length = readVarInt(byteBuf);

        if(length > maxLength * 3) {
            throw new RuntimeException("String too long");
        }

        String string = byteBuf.toString(byteBuf.readerIndex(), length, StandardCharsets.UTF_8);
        byteBuf.readerIndex(byteBuf.readerIndex() + length);

        if(string.length() > maxLength) {
            throw new RuntimeException("String too long");
        }

        return string;
    }

    /**
     * Reads a UTF-8 string from the ByteBuf with a maximum length limit
     *
     * @param byteBuf
     * @param string
     * @param maxLength
     */
    public static void writeString(ByteBuf byteBuf, String string, int maxLength) {
        if(string.length() > maxLength) {
            throw new RuntimeException("String too long");
        }

        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

        if(bytes.length > maxLength * 3) {
            throw new RuntimeException("String too long");
        }

        writeVarInt(byteBuf, bytes.length);
        byteBuf.writeBytes(bytes);
    }

}