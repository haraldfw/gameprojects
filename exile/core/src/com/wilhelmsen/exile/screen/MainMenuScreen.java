package com.wilhelmsen.exile.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.wilhelmsen.exile.ExileGame;

/**
 * Created by Harald on 28.02.2017.
 */
public class MainMenuScreen extends MenuScreen {

    public MainMenuScreen(ExileGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        float scrWidth = Gdx.graphics.getWidth();
        float scrHeight = Gdx.graphics.getHeight();
        float buttonWidth = scrWidth / 5;
        float buttonHeight = scrHeight / 10;

        TextButton gameButton = new TextButton("New Game", skin); // Use the initialized skin
        gameButton.setPosition(scrWidth / 2 - buttonWidth / 2, scrHeight / 2);
        gameButton.setSize(buttonWidth, buttonHeight);
        gameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });
        stage.addActor(gameButton);

        TextButton settingsButton = new TextButton("Settings", skin); // Use the initialized skin
        settingsButton.setPosition(scrWidth / 2 - buttonWidth / 2, scrHeight / 2 - buttonHeight * 1.5f);
        settingsButton.setSize(buttonWidth, buttonHeight);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game));
            }
        });
        stage.addActor(settingsButton);
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
}
