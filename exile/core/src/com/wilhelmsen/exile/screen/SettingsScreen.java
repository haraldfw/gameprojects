package com.wilhelmsen.exile.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.wilhelmsen.exile.ExileGame;

/**
 * Created by Harald on 28.02.2017.
 */
public class SettingsScreen extends MenuScreen {

    private Preferences prefs;
    private float scrWidth;
    private float scrHeight;
    private float buttonWidth;
    private float buttonHeight;

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

        TextFieldValidator validator = new TextFieldValidator();

        final TextField resWidthField = new TextField("res x", skin);
        resWidthField.setPosition(xPosLeft, yStart);
        resWidthField.setSize(buttonWidth, buttonHeight);
        resWidthField.setTextFieldListener(validator);
        stage.addActor(resWidthField);

        final TextField resHeightField = new TextField("res y", skin);
        resHeightField.setPosition(xPosRight + getPos(buttonWidth, 1), yStart);
        resHeightField.setSize(buttonWidth, buttonHeight);
        resHeightField.setTextFieldListener(validator);
        stage.addActor(resHeightField);


        final TextButton saveButton = new TextButton("Save", skin); // Use the initialized skin
        saveButton.setPosition(xPosLeft + getPos(buttonWidth, 1), yStart - getPos(buttonHeight, 1));
        saveButton.setSize(buttonWidth, buttonHeight);
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveButton.setChecked(false);
                int resWidth;
                int resHeight;
                try {
                    resWidth = Integer.parseInt(resWidthField.getText());
                } catch (Exception e) {
                    TextFieldValidator.setFieldInvalidInput(resWidthField);
                    return;
                }

                try {
                    resHeight = Integer.parseInt(resHeightField.getText());
                } catch (Exception e) {
                    TextFieldValidator.setFieldInvalidInput(resHeightField);
                    return;
                }
                try {
                    Aspect.getFromAspect((float) resWidth / (float) resHeight);
                } catch (Exception e) {
                    TextFieldValidator.setFieldInvalidInput(resWidthField);
                    TextFieldValidator.setFieldInvalidInput(resHeightField);
                    game.addChatMessage("Invalid aspect ratio", "debug");
                    return;
                }
                prefs.putInteger("resolution_width", resWidth);
                prefs.putInteger("resolution_height", resHeight);
                prefs.flush();
                game.addChatMessage("Settings saved", "debug");
            }
        });
        stage.addActor(saveButton);

        TextButton backButton = new TextButton("Back", skin); // Use the initialized skin
        backButton.setPosition(xPosLeft, yStart - getPos(buttonHeight, 1));
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
