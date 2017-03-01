package com.wilhelmsen.exile.screen.settings;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Created by Harald on 28.02.2017.
 */
public class NameFieldValidator implements TextField.TextFieldListener {

    public static void setFieldInvalidInput(TextField textField) {
        textField.setColor(Color.RED);
    }

    public static void setFieldValidInput(TextField textField) {
        textField.setColor(Color.GREEN);
    }

    @Override
    public void keyTyped(TextField textField, char c) {
        final int nameMinChars = 4;
            setFieldInvalidInput(textField);
        } else {
            setFieldValidInput(textField);
        }
    }
}
