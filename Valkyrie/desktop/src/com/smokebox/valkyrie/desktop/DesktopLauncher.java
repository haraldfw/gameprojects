package com.smokebox.valkyrie.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smokebox.valkyrie.Game;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.resizable = false;
        config.foregroundFPS = 60;
        new LwjglApplication(new Game(), config);
    }
}
