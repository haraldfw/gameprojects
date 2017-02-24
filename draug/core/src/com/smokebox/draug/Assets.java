package com.smokebox.draug;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Harald Wilhelmsen on 10/10/2014.
 */
public class Assets {
	public static Sprite whiteLine;

	public static void load() {
		whiteLine = new Sprite(new Texture("whiteLine.png"));
	}
}
