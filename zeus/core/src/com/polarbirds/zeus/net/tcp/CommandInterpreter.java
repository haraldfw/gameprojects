package com.polarbirds.zeus.net.tcp;

import com.polarbirds.zeus.ZeusGame;
import com.polarbirds.zeus.net.Shell;
import com.polarbirds.zeus.net.server.TCPServer;
import com.polarbirds.zeus.net.udp.UDPConnection;
import com.polarbirds.zeus.net.server.UDPServer;

/**
 * Created by Harald on 27.1.16.
 */
public class CommandInterpreter {

  private UDPServer udpServer;
  private UDPConnection udpConnection;
  private TCPServer tcpServer;
  private TCPConnection tcpConnection;
  private ZeusGame zeusGame;
  private Shell shell;

  public CommandInterpreter(UDPServer udpServer, UDPConnection udpConnection,
                            TCPServer tcpServer, TCPConnection tcpConnection,
                            ZeusGame zeusGame, Shell shell) {
    this.udpServer = udpServer;
    this.udpConnection = udpConnection;
    this.tcpServer = tcpServer;
    this.tcpConnection = tcpConnection;
    this.zeusGame = zeusGame;
    this.shell = shell;
  }

  public String interpretCommand(String command) {
    String[] commands = command.split(" ");
    switch (commands[0]) {
      case "kick":
        break;
      case "exit":
        break;
      default:
        shell.addCommand(command);
    }
    return "Invalid command";
  }
}
