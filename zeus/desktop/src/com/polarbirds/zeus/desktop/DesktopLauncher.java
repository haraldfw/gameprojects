package com.polarbirds.zeus.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.polarbirds.zeus.ZeusGame;

public class DesktopLauncher {

  public static void main(String[] args) {
    //new Thread(new UDPServer(true)).start();
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.width = ZeusGame.SCREEN_PIXELS_PER_TILESIDE * ZeusGame.X_TILES;
    config.height = ZeusGame.SCREEN_PIXELS_PER_TILESIDE * ZeusGame.Y_TILES;
    config.foregroundFPS = 60;
    config.backgroundFPS = 60;
    config.useGL30 = true;
    new LwjglApplication(new ZeusGame(), config);
  }
}
