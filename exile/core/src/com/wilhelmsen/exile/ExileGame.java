package com.wilhelmsen.exile;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wilhelmsen.exile.screen.Aspect;
import com.wilhelmsen.exile.screen.MainMenuScreen;
import com.wilhelmsen.exile.screen.overlay.Overlay;

public class ExileGame extends Game {

    public final float delta = 1f / 60f;
    public SpriteBatch gameBatch;
    public Aspect aspect;
    private Overlay overlay;

    @Override
    public void create() {
        overlay = new Overlay(this);
        gameBatch = new SpriteBatch();
        float ratio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        Aspect aspect = Aspect.getFromAspect(ratio);
        if (aspect == null) {
            addChatMessage("Invalid aspect ratio. Set resolution to 1280x720. Change it in the settings.", "debug");
            Gdx.graphics.setWindowedMode(1280, 720);
        }
        this.aspect = aspect;
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
        overlay.updateAndDraw(delta);
    }

    @Override
    public void dispose() {
        gameBatch.dispose();
    }

    public void addChatMessage(String message, String sender) {
        overlay.chat.addMessage(message, sender);
    }
}
