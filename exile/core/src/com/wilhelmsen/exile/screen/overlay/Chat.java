package com.wilhelmsen.exile.screen.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.google.common.base.Splitter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Harald on 28.02.2017.
 */
public class Chat {

    private final int maxEntries = 20;
    private final int entriesOnUpdate = 3;
    private final int maxCharactersWidth = 50;
    private final int showNewMessagesForSeconds = 5;
    private final SimpleDateFormat simpleDateFormat;
    private LinkedList<String> messages;
    private float secondsSinceLastMessage = Float.POSITIVE_INFINITY;
    private boolean isActive = false;

    private TextField textInput;
    private Label textMessageLabel;

    private Group group;
    private Overlay overlay;
    private Stage stage;

    public Chat(Stage stage, Overlay overlay) {
        this.overlay = overlay;
        this.stage = stage;
        messages = new LinkedList<>();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Skin skin = createBasicSkin();
        textInput = new TextField("", skin);
        textInput.setPosition(0, 0);
        textInput.setSize(400, 40);
        group = new Group();
        textMessageLabel = new Label("", skin);
        textMessageLabel.setAlignment(Align.topLeft);
        textMessageLabel.setPosition(0, 40);
        group.addActor(textInput);
        group.addActor(textMessageLabel);
        stage.addActor(group);
    }

    public void update(float delta) {
        secondsSinceLastMessage += delta;

        if (isActive) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                exitChat();
                isActive = !isActive;
            }
        } else if (secondsSinceLastMessage > showNewMessagesForSeconds) {
            textMessageLabel.setText(null);
            textMessageLabel.setVisible(false);
        }
    }

    private void exitChat() {
        textInput.setText("");
        textInput.setVisible(false);
        overlay.relieveKeyboardFocus();
        overlay.relieveInput();
    }

    private void updateMessageLabel(int messageAmount) {
        List<String> msgs = new ArrayList<>();
        int endIndex = messages.size() - messageAmount;
        for (int i = messages.size() - 1; i >= 0 && i >= endIndex; i--) {
            msgs.add(0, messages.get(i));
        }
        textMessageLabel.setVisible(true);
        textMessageLabel.setText(String.join("\n", msgs));
        textMessageLabel.setPosition(0, 40 + msgs.size() * textMessageLabel.getStyle().font.getLineHeight());
    }

    void chatActivationKeyPressed() {
        if (isActive) {
            addMessage(textInput.getText(), "you");
            exitChat();
        } else {
            updateMessageLabel(maxEntries);
            group.getChildren().forEach(actor -> actor.setVisible(true));
            overlay.claimKeyboardFocus(textInput);
            overlay.claimInput(stage);
        }
        isActive = !isActive;
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
        updateMessageLabel(entriesOnUpdate);
    }

    private void addToMessageList(String message) {
        messages.add(message);
        if (messages.size() > maxEntries) {
            messages.removeFirst();
        }
    }

    // Here for debugging !duplicate code!
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

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        ;
        labelStyle.fontColor = Color.WHITE;
        labelStyle.background = skin.newDrawable("background", new Color(0.1f, 0.1f, 0.1f, 1));
        skin.add("default", labelStyle);

        skin.add("default", textFieldStyle);
        return skin;
    }
}
