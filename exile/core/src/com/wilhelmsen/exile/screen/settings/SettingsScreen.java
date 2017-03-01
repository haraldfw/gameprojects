package com.wilhelmsen.exile.screen.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.wilhelmsen.exile.ExileGame;
import com.wilhelmsen.exile.screen.MainMenuScreen;
import com.wilhelmsen.exile.screen.MenuScreen;

/**
 * Created by Harald on 28.02.2017.
 */
public class SettingsScreen extends MenuScreen {

    Preferences prefs;
    float scrWidth;
    float scrHeight;
    float buttonWidth;
    float buttonHeight;

    public SettingsScreen(ExileGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        prefs = Gdx.app.getPreferences("exileSettings.xml");

        scrWidth = Gdx.graphics.getWidth();
        scrHeight = Gdx.graphics.getHeight();
        buttonWidth = scrWidth / 5;
        buttonHeight = scrHeight / 10;
        float xPosLeft = buttonWidth / 2;
        float xPosRight = buttonWidth / 2;
        float yStart = scrHeight / 2;

        final TextField nameField = new TextField("player name", skin);
        nameField.setPosition(xPosLeft, yStart);
        nameField.setSize(buttonWidth, buttonHeight);
        nameField.setTextFieldListener(new NameFieldValidator());
        nameField.setText(prefs.getString("player_name"));
        stage.addActor(nameField);

        ResolutionFieldValidator validator = new ResolutionFieldValidator();

        final TextField resWidthField = new TextField("res x", skin);
        resWidthField.setPosition(xPosLeft, yStart - getPos(buttonHeight, 1));
        resWidthField.setSize(buttonWidth, buttonHeight);
        resWidthField.setTextFieldListener(validator);
        resWidthField.setText(prefs.getString("resolution_width"));
        stage.addActor(resWidthField);

        final TextField resHeightField = new TextField("res y", skin);
        resHeightField.setPosition(xPosRight + getPos(buttonWidth, 1), yStart - getPos(buttonHeight, 1));
        resHeightField.setSize(buttonWidth, buttonHeight);
        resHeightField.setTextFieldListener(validator);
        resHeightField.setText(prefs.getString("resolution_height"));
        stage.addActor(resHeightField);


        final TextButton saveButton = new TextButton("Save", skin); // Use the initialized skin
        saveButton.setPosition(xPosLeft + getPos(buttonWidth, 2), yStart - getPos(buttonHeight, 2));
        saveButton.setSize(buttonWidth, buttonHeight);
        saveButton.addListener(new SaveListener(game, resWidthField, resHeightField, nameField, prefs, saveButton));
        stage.addActor(saveButton);

        TextButton backButton = new TextButton("Back", skin); // Use the initialized skin
        backButton.setPosition(xPosLeft, yStart - getPos(buttonHeight, 2));
        backButton.setSize(buttonWidth, buttonHeight);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(backButton);
    }

    private float getPos(float btnSize, int tableYIndex) {
        return 1.1f * (float) tableYIndex * btnSize;
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
