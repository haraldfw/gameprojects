package com.wilhelmsen.exile.screen.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wilhelmsen.exile.ExileGame;

/**
 * Created by Harald on 01.03.2017.
 */
public class Overlay {

    public Chat chat;
    private ExileGame game;
    private Stage stage;
    private Actor prevFocus;
    private InputProcessor prevProcessor;

    public Overlay(ExileGame game) {
        this.game = game;
        stage = new Stage();
        chat = new Chat(stage, this);
    }

    public void updateAndDraw(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            chat.chatActivationKeyPressed();
        }
        stage.act();
        chat.update(delta);
        stage.draw();
    }

    void claimKeyboardFocus(Actor actor) {
        prevFocus = stage.getKeyboardFocus();
        stage.setKeyboardFocus(actor);
    }

    void relieveKeyboardFocus() {
        stage.setKeyboardFocus(prevFocus);
    }

    void claimInput(InputProcessor inputProcessor) {
        prevProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);
    }

    void relieveInput() {
        Gdx.input.setInputProcessor(prevProcessor);
    }
}
