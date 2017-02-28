package com.wilhelmsen.exile.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wilhelmsen.exile.ExileGame;

/**
 * Created by Harald on 26.02.2017.
 */
public class GameScreen extends ExileScreen {

    private OrthographicCamera camera;

    public GameScreen(ExileGame game) {
        super(game);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.aspect.frustumWidth, game.aspect.frustumHeight);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.gameBatch.setProjectionMatrix(camera.combined);
        updateCamera();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void updateCamera() {

    }
}
