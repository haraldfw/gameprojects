package com.smokebox.draug;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.smokebox.draug.testBed.FractalScreen;

public class DraugGame extends Game {

	public SpriteBatch batch;
	public OrthographicCamera cam;
	public static AssetManager astmng;

	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 32, 24);
		Assets.load();
		//setScreen(new MainMenuScreen(this));
		//setScreen(new GameScreen(this));
		//setScreen(new MiniGameScreen(this));
		//setScreen(new TestBedScreen(this));
		setScreen(new FractalScreen(this));
	}

	@Override
	public void render() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0.1f, 0.1f, 0.1f, 0.0f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();

		Screen s = super.getScreen();
		if (s != null) s.render(1f / 60f);
	}

	public Vector2 mousePos() {
		Vector3 v = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		return new Vector2(v.x, v.y);
	}
}
