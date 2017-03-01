package com.wilhelmsen.exile.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.wilhelmsen.exile.ExileGame;

/**
 * Created by Harald on 28.02.2017.
 */
public abstract class MenuScreen extends ExileScreen {

    public Skin skin;
    public Stage stage;

    public MenuScreen(ExileGame game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events

        skin = createBasicSkin();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

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

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = skin.newDrawable("background", Color.DARK_GRAY);
        textFieldStyle.cursor = skin.newDrawable("cursor", Color.LIGHT_GRAY);
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.LIGHT_GRAY;
        textFieldStyle.selection = skin.newDrawable("background", 0.5f, 0.5f, 0.5f, 0.5f);
        textFieldStyle.focusedFontColor = Color.WHITE;

        skin.add("default", textFieldStyle);
        return skin;
    }
}
