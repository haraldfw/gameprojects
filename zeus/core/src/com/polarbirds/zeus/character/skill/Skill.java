package com.polarbirds.zeus.character.skill;

import com.polarbirds.zeus.ZeusGame;

/**
 * Created by Harald on 02.02.2016.
 */
public abstract class Skill {

  protected final ZeusGame game;

  public Skill(ZeusGame game) {
    this.game = game;
  }

  public abstract void activate();
}
