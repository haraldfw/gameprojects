package com.polarbirds.zeus.input;

import com.badlogic.gdx.Input;

/**
 * Created by Harald on 10.02.2016.
 */
public enum Key {
  PLAYER_MOVE_UP(Input.Keys.W),
  PLAYER_MOVE_LEFT(Input.Keys.A),
  PLAYER_MOVE_DOWN(Input.Keys.S),
  PLAYER_MOVE_RIGHT(Input.Keys.D),
  MENU_SELECT(Input.Keys.ENTER),
  MENU_UP(Input.Keys.UP),
  MENU_LEFT(Input.Keys.LEFT),
  MENU_DOWN(Input.Keys.DOWN),
  MENU_RIGHT(Input.Keys.RIGHT),
  TOGGLE_CHAT(Input.Keys.ENTER);

  static {
    for (Key key:
         Key.values()) {
      System.out.println(key.toString() + ": " + key.value);
    }
  }

  public int value;

  Key(int value) {
    this.value = value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public static Key getFromString(String keyName) {
    for (Key key : Key.values()) {
      if (keyName.equals(key.toString())) {
        return key;
      }
    }
    return null;
  }
}
