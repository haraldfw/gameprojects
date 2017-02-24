package com.polarbirds.zeus.net.server;

import com.polarbirds.zeus.net.Shell;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Harald on 14.1.16.
 */
public class UDPServer implements Runnable {

  public static final int appID = 1953129815;
  private static final int UDPPORT = 6767;
  private DatagramSocket udpSocket;
  private Socket tcpSocket;
  private boolean running = true;
  private ArrayList<InetAddress> connectedIPs;

  public static byte[] ID_BYTES = ByteBuffer.allocate(4).putInt(0, appID).array();

  public UDPServer(boolean createLocalShell) {
    if (createLocalShell) {
      new Thread(new Shell(this)).start();
    }
    try {
      udpSocket = new DatagramSocket(UDPPORT);
    } catch (SocketException e) {
      e.printStackTrace();
    }
    System.out.println("UDPServer started");
  }

  public void run() {
    while (running) {
      byte[] data = new byte[1024];
      DatagramPacket packet = new DatagramPacket(data, data.length);
      try {
        udpSocket.receive(packet);
      } catch (IOException e) {
        if (udpSocket.isClosed()) {
          System.out.println("Socket closed");
          break;
        } else {
          e.printStackTrace();
        }
      }
      byte[] recId = new byte[4];
      System.arraycopy(packet.getData(), 0, recId, 0, 4);
      if(Arrays.equals(ByteBuffer.wrap(recId).array(), ID_BYTES)) {
        String msg = new String(packet.getData());
        if (msg.trim().equalsIgnoreCase("ping")) {
          sendData("pong".getBytes(), packet.getAddress());
        }
        System.out.println("CLIENT > " + msg);
      }
    }
    System.out.println("UDPServer stopped");
  }

  public void stopServer() {
    running = false;
    udpSocket.close();
  }

  public void sendData(byte[] data, InetAddress ipAddress) {
    DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, UDPPORT);
    try {
      udpSocket.send(packet);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public byte[] generatePacket() {
    byte[] packet = new byte[1024];
    System.arraycopy(ID_BYTES, 0, packet, 0, 4);
    return packet;
  }
}
