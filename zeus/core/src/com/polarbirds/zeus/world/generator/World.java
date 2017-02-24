package com.polarbirds.zeus.world.generator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.polarbirds.zeus.world.Tile;

import java.util.HashMap;

/**
 * Created by Harald on 30.01.2016.
 */
public class World {

  public Vector2 spawn;
  public Tile[][] tiles;
  private HashMap<Tile.TileGraphic, Texture> textures;

  World(Vector2 spawn, Tile[][] tiles, HashMap<Tile.TileGraphic, Texture> textures) {
    this.spawn = spawn;
    this.tiles = tiles;
    this.textures = textures;
  }

  public void drawFloor(SpriteBatch batch) {
    for (int x = 0; x < tiles.length; x++) {
      for (int y = 0; y < tiles[0].length; y++) {
        Tile tile = tiles[x][y];
        if (tile.graphic == Tile.TileGraphic.FLOOR_BASIC) {
          batch.draw(textures.get(tile.graphic), x, y, 1, 1);
        }
      }
    }
  }

  public void drawWalls(SpriteBatch batch) {
    for (int x = 0; x < tiles.length; x++) {
      for (int y = 0; y < tiles[0].length; y++) {
        Tile tile = tiles[x][y];
        if(tile.graphic == Tile.TileGraphic.WALL_BASIC) {
          batch.draw(textures.get(Tile.TileGraphic.WALL_BASIC), x, y + 1, 1, 1);
          if(y - 1 < 0 || tiles[x][y - 1].collision == Tile.TileCollision.FLOOR) {
            batch.draw(textures.get(Tile.TileGraphic.WALL_SIDE), x, y, 1, 1);
          }
        }
      }
    }
  }

  public enum WorldType {
    HUB,
    DUNGEON;
  }
}
