package com.polarbirds.zeus.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Harald on 13.01.2016.
 */
public class UDPConnection extends Thread {

  private static final int PORT = 6767;
  DatagramSocket socket;
  InetAddress ipAddress;
  private boolean stayConnected = true;

  public UDPConnection(String ipAddress) {
    try {
      socket = new DatagramSocket();
      this.ipAddress = InetAddress.getByName(ipAddress);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    while (stayConnected) {
      byte[] data = new byte[1024];
      DatagramPacket packet = new DatagramPacket(data, data.length);
      try {
        socket.receive(packet);
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("SERVER > " + new String(packet.getData()));
    }
  }

  public void sendData(byte[] data) {
    DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, PORT);
    try {
      socket.send(packet);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void disconnect() {
    stayConnected = false;
  }
}
