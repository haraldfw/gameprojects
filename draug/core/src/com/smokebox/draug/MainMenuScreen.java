package com.smokebox.draug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Harald Wilhelmsen on 10/10/2014.
 */
public class MainMenuScreen extends ScreenAdapter {

	DraugGame game;
	OrthographicCamera cam;

	Rectangle playBound;
	Rectangle quitBound;

	Vector3 touchPoint;

	public MainMenuScreen(DraugGame game) {
		this.game = game;
		cam = new OrthographicCamera(32, 24);
		game.batch.setProjectionMatrix(cam.combined);

		playBound = new Rectangle(8, 16, 16, 4);
		quitBound = new Rectangle(8, 10, 16, 4);
		touchPoint = new Vector3();
	}

	@Override
	public void render(float delta) {

		if(Gdx.input.justTouched()) {
			cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			if(playBound.contains(touchPoint.x, touchPoint.y)) {
				System.out.println("Sending to screen GameScreen");
				game.setScreen(new GameScreen(game));
				return;
			}
		}
	}
}
