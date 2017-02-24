package com.polarbirds.zeus.character.weapon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polarbirds.zeus.ZeusGame;

/**
 * Created by Harald on 02.02.2016.
 */
public abstract class Weapon {

  public Occupy occupies;
  protected final ZeusGame game;

  public Weapon(Occupy occupies, ZeusGame game) {
    this.occupies = occupies;
    this.game = game;
  }

  public abstract void shoot();
  public abstract void update(float delta);
  public abstract void draw(SpriteBatch sb);

  public enum Occupy {
    TWO_HANDED,
    ONE_HANDED;
  }
}
