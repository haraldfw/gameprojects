package com.wilhelmsen.exile.screen.settings;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wilhelmsen.exile.ExileGame;
import com.wilhelmsen.exile.screen.Aspect;

/**
 * Created by Harald on 01.03.2017.
 */
public class SaveListener extends ClickListener {
    private ExileGame game;
    private TextField resWidthField;
    private TextField resHeightField;
    private TextField nameField;
    private Preferences prefs;
    private TextButton saveButton;

    public SaveListener(ExileGame game, TextField resWidthField, TextField resHeightField, TextField nameField, Preferences prefs, TextButton saveButton) {
        this.game = game;
        this.resWidthField = resWidthField;
        this.resHeightField = resHeightField;
        this.nameField = nameField;
        this.prefs = prefs;
        this.saveButton = saveButton;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        saveButton.setChecked(false);
        int resWidth;
        int resHeight;
        try {
            resWidth = Integer.parseInt(resWidthField.getText());
        } catch (Exception e) {
            ResolutionFieldValidator.setFieldInvalidInput(resWidthField);
            return;
        }

        try {
            resHeight = Integer.parseInt(resHeightField.getText());
        } catch (Exception e) {
            ResolutionFieldValidator.setFieldInvalidInput(resHeightField);
            return;
        }
        try {
            Aspect.getFromAspect((float) resWidth / (float) resHeight);
        } catch (Exception e) {
            ResolutionFieldValidator.setFieldInvalidInput(resWidthField);
            ResolutionFieldValidator.setFieldInvalidInput(resHeightField);
            game.addChatMessage("Invalid aspect ratio", "debug");
            return;
        }
        prefs.putInteger("resolution_width", resWidth);
        prefs.putInteger("resolution_height", resHeight);
        prefs.putString("player_name", nameField.getText());
        prefs.flush();
        game.addChatMessage("Settings saved", "debug");
    }
}
