package com.polarbirds.zeus.net;

import com.polarbirds.zeus.net.server.UDPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Harald on 14.1.16.
 */
public class Shell implements Runnable {

  private UDPServer server;
  private ArrayList<String> queue;
  boolean running = true;

  public Shell(UDPServer server) {
    this.server = server;
  }

  @Override
  public void run() {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    while (running) {
      String line = null;
      try {
        line = in.readLine().trim();
        interpretCommand(line);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private synchronized void interpretCommand(String command) {
    if (command.equalsIgnoreCase("stop") || command.equalsIgnoreCase("quit")) {
      server.stopServer();
      running = false;
    }
  }

  public void addCommand(String command) {
    queue.add(command);
  }
}
