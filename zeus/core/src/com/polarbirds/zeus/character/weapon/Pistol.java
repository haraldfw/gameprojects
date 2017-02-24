package com.polarbirds.zeus.character.weapon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polarbirds.zeus.ZeusGame;

/**
 * Created by Harald on 02.02.2016.
 */
public class Pistol extends Weapon {

  public Pistol(Occupy occupies, ZeusGame game) {
    super(occupies, game);
  }

  public Pistol(ZeusGame game) {
    super(Occupy.ONE_HANDED, game);
  }

  @Override
  public void shoot() {

  }

  @Override
  public void update(float delta) {

  }

  @Override
  public void draw(SpriteBatch sb) {

  }
}
