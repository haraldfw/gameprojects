package com.polarbirds.zeus.world.physics.shape;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Harald on 02.02.2016.
 */
public class Circle {

  public float radius;
  public Vector2 pos;

  public Circle(float radius, Vector2 pos) {
    this.radius = radius;
    this.pos = pos;
  }
}
