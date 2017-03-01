package com.wilhelmsen.exile.connection;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Harald on 01.03.2017.
 */
public class Packet {
    private LinkedList<Byte> packetBytes;

    public Packet() {
        packetBytes = new LinkedList<>();
    }

    private void appendFloat(float value) {
        byte[] bytes = ByteBuffer.allocate(4).putFloat(value).array();
        byte floatByteId = 1;
        packetBytes.add(floatByteId);
        packetBytes.add((byte) bytes.length);
        for (byte b : bytes) {
            packetBytes.add(b);
        }
    }

    private void appendString(String string) {
        byte[] bytes = string.getBytes();
        byte stringByteId = 0;
        packetBytes.add(stringByteId);
        packetBytes.add((byte) bytes.length);
        for (Byte b : bytes) {
            packetBytes.add(b);
        }
    }
}
