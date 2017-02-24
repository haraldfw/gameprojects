package com.polarbirds.wendigo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.polarbirds.wendigo.world.WendigoWorld;

public class GameScreen implements Screen {

    private static int TILESIZE = 16; // 16 in-game pixels per tile

    private WendigoWorld world = new WendigoWorld();

    private Game game;

    private float TILES_WIDTH = (float) Gdx.graphics.getWidth() / TILESIZE;
    private float TILES_HEIGHT = (float) Gdx.graphics.getHeight() / TILESIZE;

    private OrthographicCamera camera = new OrthographicCamera(TILES_WIDTH, TILES_HEIGHT);
    private Viewport viewport = new FitViewport(TILES_WIDTH, TILES_HEIGHT, camera);
    Stage stage = new Stage(viewport);
    private SpriteBatch batch = new SpriteBatch();

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        world.draw(batch);
        batch.end();
    }

    @Override
    public void show() {
        batch.setTransformMatrix(camera.combined);
    }

    @Override
    public void hide() {

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
    public void dispose() {
        batch.dispose();
    }
}
