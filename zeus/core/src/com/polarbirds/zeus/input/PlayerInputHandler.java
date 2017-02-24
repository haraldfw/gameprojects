package com.polarbirds.zeus.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.polarbirds.zeus.character.Player;

/**
 * Created by Harald on 10.02.2016.
 */
public class PlayerInputHandler {

  private Player player;

  public PlayerInputHandler(Player player) {
    this.player = player;
  }

  public void takeMovement() {
    if(Gdx.input.isKeyPressed(Key.PLAYER_MOVE_UP.value)) {
      player.applyWalkForce(0, 1);
    }
    if(Gdx.input.isKeyPressed(Key.PLAYER_MOVE_LEFT.value)) {
      player.applyWalkForce(-1, 0);
    }
    if(Gdx.input.isKeyPressed(Key.PLAYER_MOVE_DOWN.value)) {
      player.applyWalkForce(0, -1);
    }
    if(Gdx.input.isKeyPressed(Key.PLAYER_MOVE_RIGHT.value)) {
      player.applyWalkForce(1, 0);
    }
  }

  public void takeKey(Key key) {
    switch (key) {

    }
  }
}
