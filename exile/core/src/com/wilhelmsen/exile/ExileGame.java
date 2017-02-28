package com.wilhelmsen.exile;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wilhelmsen.exile.screen.Aspect;
import com.wilhelmsen.exile.screen.GameScreen;

public class ExileGame extends Game {

    public SpriteBatch batch;
    public final float delta = 1f / 60f;

    public Aspect aspect;

    @Override
    public void create() {
        batch = new SpriteBatch();
        float ratio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        Aspect aspect = Aspect.getFromAspect(ratio);
        if (aspect == null) {
            System.out.println("Invalid aspect ratio of " + ratio);
            System.exit(-1);
        }
        this.aspect = aspect;
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
