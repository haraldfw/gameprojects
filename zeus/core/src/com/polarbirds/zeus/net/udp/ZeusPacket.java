package com.polarbirds.zeus.net.udp;

import com.polarbirds.zeus.ZeusGame;

/**
 * Created by Harald on 17.02.2016.
 */
public class ZeusPacket {

  /*
    packet: 0->4:appId 4:packetType 5++:packetData
   */

  public final PacketType type;

  public ZeusPacket(byte id) {
    this.type = PacketType.get(id);
  }

  public enum PacketType {
    LOGIN((byte) 0),
    LOGOUT((byte) 1);

    private final byte id;

    PacketType(byte id) {
      this.id = id;
    }

    public static PacketType get(byte b) {
      for (PacketType p : PacketType.values()) {
        if (b == p.id) {
          return p;
        }
      }
      System.out.println("No packet-type exists for id-byte: " + b + ". Returning null");
      return null;
    }

    public static void parse(byte[] data, ZeusGame game) {
      byte id = data[4];
      if (id == LOGIN.id) {

      } else if (id == LOGOUT.id) {

      }

    }
  }
}
