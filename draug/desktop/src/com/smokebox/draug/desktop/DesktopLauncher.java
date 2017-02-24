package com.smokebox.draug.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smokebox.draug.DraugGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 32*60;
		config.height = 24*60;
		config.foregroundFPS = 60;
		new LwjglApplication(new DraugGame(), config);
	}
}
