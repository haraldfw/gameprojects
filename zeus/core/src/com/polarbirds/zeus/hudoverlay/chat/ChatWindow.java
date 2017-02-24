package com.polarbirds.zeus.hudoverlay.chat;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.polarbirds.zeus.ZeusGame;
import com.polarbirds.zeus.input.Focus;
import com.polarbirds.zeus.input.Key;

import java.util.ArrayList;

/**
 * Created by Harald on 23.01.2016.
 */
public class ChatWindow {

  ChatMode chatMode = ChatMode.ALWAYS;

  static final int TIME_MSG_NEW = 2500;
  static final int MAX_MSG_SIZE = 500;
  static final int MAX_LIST_SIZE = 10;
  static final int CHAT_INACTIVE_SHOW_ITEMS = 5;
  static final float X_SHIFT = 1;
  static final float Y_SHIFT = 1;
  static final float LINE_HEIGHT = 0.5f;
  BitmapFont font;
  ArrayList<ChatMsg> chatMsgs;

  TextField textField;

  ZeusGame game;

  public ChatWindow(ZeusGame game) {
    this.game = game;
    FreeTypeFontGenerator fg = new FreeTypeFontGenerator(new FileHandle("data/font.ttf"));
    font = fg.generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter());
    chatMsgs = new ArrayList<>();
    textField = new TextField(font, game);
    addMsg("Chat started successfully");
  }

  public void handleInputEvent(InputEvent event) {
    if (event.getKeyCode() == Key.TOGGLE_CHAT.value) {
      if (game.focus == Focus.CHAT) {
        String text = textField.getText().trim();
        if (!text.isEmpty()) {
          addMsg("you: " + text);
          textField.clearText();
        }
        game.setFocus(Focus.GAME);
      }
    } else {
      textField.handleInputEvent(event);
    }
  }

  public void render(SpriteBatch spriteBatch) {
    boolean chatFocus = game.focus == Focus.CHAT;
    if (chatMode != ChatMode.HIDE) {
      for (int i = 0; i < chatMsgs.size(); i++) {
        if (!chatFocus && i >= CHAT_INACTIVE_SHOW_ITEMS) {
          break;
        }
        ChatMsg msg = chatMsgs.get(i);
        if (chatMode != ChatMode.ALWAYS &&
            !chatFocus && System.currentTimeMillis() > msg.time + TIME_MSG_NEW) {
          break;
        }
        font.draw(spriteBatch, msg.toString(), ZeusGame.SCREEN_PIXELS_PER_TILESIDE * X_SHIFT,
            ZeusGame.SCREEN_PIXELS_PER_TILESIDE * (i * LINE_HEIGHT + Y_SHIFT));
      }
    }
    if (chatFocus) {
      textField.draw(spriteBatch);
    }
  }

  public void addMsg(String msg) {
    if (msg.length() > MAX_MSG_SIZE) {
      msg = msg.substring(0, MAX_MSG_SIZE);
    }
    chatMsgs.add(0, new ChatMsg(msg));
    while (chatMsgs.size() > MAX_LIST_SIZE) {
      chatMsgs.remove(chatMsgs.size() - 1);
    }
  }
}
