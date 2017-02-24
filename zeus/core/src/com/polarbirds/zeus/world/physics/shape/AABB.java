package com.polarbirds.zeus.world.physics.shape;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Harald on 02.02.2016.
 */
public class AABB {
  public Vector2 pos;
  public Vector2 dim;

  public AABB(Vector2 pos, Vector2 dim) {
    this.pos = pos;
    this.dim = dim;
  }
}
