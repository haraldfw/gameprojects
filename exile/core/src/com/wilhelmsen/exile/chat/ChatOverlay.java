package com.wilhelmsen.exile.chat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.google.common.base.Splitter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Harald on 28.02.2017.
 */
public class ChatOverlay {

    private final int maxEntries = 20;
    private final int entriesOnUpdate = 3;
    private final int maxCharactersWidth = 50;
    private final int showNewMessagesForSeconds = 5;
    private final SimpleDateFormat simpleDateFormat;
    private final float xShift = 20;
    private final float yShiftPerLine = 3;
    private LinkedList<String> messages;
    private float secondsSinceLastMessage = Float.POSITIVE_INFINITY;
    private boolean isActive = false;
    private SpriteBatch chatBatch;
    private BitmapFont chatFont;

    private TextField textInput;
    private Stage stage;

    public ChatOverlay() {
        this.chatBatch = new SpriteBatch();
        messages = new LinkedList<>();
        simpleDateFormat = new SimpleDateFormat("H:m:s");
        chatFont = new BitmapFont();
        chatFont.setColor(Color.WHITE);
        stage = new Stage();
        textInput = new TextField("", createBasicSkin());
        textInput.setPosition(0, 0);
        textInput.setSize(400, chatFont.getLineHeight()*1.2f);
        stage.addActor(textInput);
    }

    public void draw(float delta) {
        secondsSinceLastMessage += delta;

        chatBatch.begin();
        if (isActive) {
            drawTextField();
            drawMessages(maxEntries);
        } else if (secondsSinceLastMessage < showNewMessagesForSeconds) {
            drawMessages(entriesOnUpdate);
        }
        chatBatch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(isActive) {
                addMessage(textInput.getText(), "you");
                textInput.setText("");
            } else {
                Gdx.input.setInputProcessor(stage);
                stage.setKeyboardFocus(textInput);
            }
            isActive = !isActive;
        }
    }

    private void drawTextField() {
//        textInput.draw(chatBatch, 1);
        stage.act();
        stage.draw();
    }

    private void drawMessages(int messageAmount) {
        int endIndex = messages.size() - messageAmount;
        float shiftIndex = 0;
        for (int i = messages.size() - 1; i >= 0 && i >= endIndex; i--) {
            shiftIndex++;
            String message = messages.get(i);
            chatFont.draw(chatBatch, message, xShift, chatFont.getLineHeight() * (shiftIndex + yShiftPerLine));
        }
    }

    public void addMessage(String message, String sender) {
        secondsSinceLastMessage = 0;
        message = simpleDateFormat.format(new Date()).concat(" ").concat(sender).concat(": ").concat(message);
        if (message.length() <= maxCharactersWidth) {
            addToMessageList(message);
        } else {
            Iterable<String> splits = Splitter.fixedLength(maxCharactersWidth).split(message);
            for (String part : splits) {
                addToMessageList(part);
            }
        }
    }

    private void addToMessageList(String message) {
        messages.add(message);
        if (messages.size() > maxEntries) {
            messages.removeFirst();
        }
    }



    // Here for debugging
    private Skin createBasicSkin() {
        //Create a font
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int) (Gdx.graphics.getWidth() / 4f),
                (int) (Gdx.graphics.getHeight() / 10f), Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));
        skin.add("cursor", new Texture(new Pixmap(2, 20, Pixmap.Format.RGB888)));

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = skin.newDrawable("background", Color.DARK_GRAY);
        textFieldStyle.cursor = skin.newDrawable("cursor", Color.LIGHT_GRAY);
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.LIGHT_GRAY;
        textFieldStyle.focusedFontColor = Color.WHITE;

        skin.add("default", textFieldStyle);
        return skin;
    }
}
