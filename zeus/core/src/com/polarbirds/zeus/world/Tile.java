package com.polarbirds.zeus.world;

/**
 * Created by Harald on 30.01.2016.
 */
public class Tile {

  public TileCollision collision;
  public TileGraphic graphic;

  public Tile(TileCollision collision, TileGraphic graphic) {
    this.collision = collision;
    this.graphic = graphic;
  }

  public enum TileCollision {
    WALL,
    FLOOR;
  }

  public enum TileGraphic {
    WALL_BASIC,
    WALL_SIDE,
    WALL_SPECIAL1,
    WALL_SPECIAL2,
    FLOOR_BASIC,
    FLOOR_BASIC2,
    FLOOR_BROKEN_WALL,
    FLOOR_SPECIAL1;
  }
}
