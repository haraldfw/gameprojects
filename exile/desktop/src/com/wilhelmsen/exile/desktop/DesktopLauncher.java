package com.wilhelmsen.exile.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wilhelmsen.exile.ExileGame;
import com.wilhelmsen.exile.screen.Aspect;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Exile";
        // TODO Set screen resolution from preferences
        cfg.width = 1280;
        cfg.height = 720;
        cfg.resizable = false;
        cfg.foregroundFPS = 60;
        cfg.backgroundFPS = cfg.foregroundFPS;
        new LwjglApplication(new ExileGame(), cfg);
    }
}
