package com.polarbirds.zeus;

import com.polarbirds.zeus.hudoverlay.chat.ChatMode;
import com.polarbirds.zeus.input.Key;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by Harald on 29.01.2016.
 */
public class Config {

  private static Config instance;
  private Preferences prefs;

  private Config() {
    prefs = Preferences.userRoot().node("zeus");
  }

  public void setSetting(String statName, String value) {
  }

  public void initPrefs() {
    try {
      // chat
      Preferences.userRoot().node("chat").removeNode();
      if (prefs.nodeExists("chat")) {
        prefs.node("chat").removeNode();
      }
      Preferences chat = prefs.node("chat");
      chat.putInt("mode", ChatMode.WHEN_ACTIVE.ordinal());
      // keys
      Preferences.userRoot().node("keys").removeNode();
      if (prefs.nodeExists("keys")) {
        prefs.node("chat").removeNode();
      }
      Preferences keys = prefs.node("chat");
      for (Key key : Key.values()) {
        keys.putInt(key.toString(), key.value);
      }
    } catch (BackingStoreException e) {
      e.printStackTrace();
    }
  }

  public static Config getInstance() {
    if (instance == null) {
      instance = new Config();
    }
    return instance;
  }
}
