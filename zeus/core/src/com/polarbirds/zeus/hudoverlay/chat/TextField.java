package com.polarbirds.zeus.hudoverlay.chat;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.polarbirds.zeus.ZeusGame;
import com.polarbirds.zeus.input.Key;

/**
 * Created by Harald on 25.1.16.
 */
public class TextField {

  GlyphLayout glyphLayout;
  private BitmapFont font;
  private Sprite cursorSprite = new Sprite(new Texture("data/cursor.png"));
  private StringBuilder text;
  private int cursor;
  private ZeusGame game;

  public TextField(BitmapFont font, ZeusGame game) {
    this.font = font;
    this.game = game;
    text = new StringBuilder();
    cursor = 0;
    cursorSprite.setScale(1, 25);
    glyphLayout = new GlyphLayout();
  }

  public String getText() {
    return text.toString();
  }

  public void draw(SpriteBatch sb, BitmapFont font) {
    font.draw(sb, text.toString(), 10, 10);
  }

  public void draw(SpriteBatch sb) {
    String text = getText();
    font.draw(sb, text, ZeusGame.SCREEN_PIXELS_PER_TILESIDE, 25);
    glyphLayout.setText(font, text);
    cursorSprite.setPosition(ZeusGame.SCREEN_PIXELS_PER_TILESIDE + glyphLayout.width, 25);
    cursorSprite.draw(sb);
  }

  public void clearText() {
    text.setLength(0);
    cursor = 0;
  }

  public void handleInputEvent(InputEvent event) {
    text.insert(cursor++, event.getCharacter()); // put c at cursor and move cursor to right
  }

  public void handleInput(Key key) {
    switch (key.value) {
      case Input.Keys.BACKSPACE :
        if (cursor > 0) {
          text.deleteCharAt(text.length() - 1);
          cursor--;
        }
        break;
    }
  }

}
