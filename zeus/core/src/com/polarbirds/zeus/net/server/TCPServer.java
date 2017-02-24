package com.polarbirds.zeus.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Harald on 26.1.16.
 */
public class TCPServer implements Runnable {

  public static final int TCPPORT = 6768;
  private boolean running = true;
  private UDPServer udpServer;


  public TCPServer(UDPServer udpServer) {
    this.udpServer = udpServer;
  }

  @Override
  public void run() {
    try {
      ServerSocket server = new ServerSocket(TCPPORT);
      while (running) {
        Socket socket = server.accept();
        new Thread(new ServerThread(udpServer, socket)).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private class ServerThread implements Runnable {

    private UDPServer udpServer;
    private Socket socket;

    public ServerThread(UDPServer udpServer, Socket socket) {
      this.udpServer = udpServer;
      this.socket = socket;
    }

    @Override
    public void run() {
      try {
        InputStream sockInput = socket.getInputStream();
        int len = 0;
        int expectedLength = sockInput.available();
        byte[] buf = new byte[expectedLength];
        while (len != buf.length) {
          len += sockInput.read(buf, len, buf.length);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
