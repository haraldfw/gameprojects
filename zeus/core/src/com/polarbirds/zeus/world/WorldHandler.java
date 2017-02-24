package com.polarbirds.zeus.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.polarbirds.zeus.character.Player;
import com.polarbirds.zeus.world.generator.World;
import com.polarbirds.zeus.world.generator.WorldGenerator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Harald on 13.01.2016.
 */
public class WorldHandler {

  public Player localPlayer;
  public ArrayList<Player> connectedPlayers;
  private World world;

  public WorldHandler(Player localPlayer) {
    this.localPlayer = localPlayer;
    connectedPlayers = new ArrayList<>();
  }

  public void addConnectedPlayer(Player player) {
    connectedPlayers.add(player);
  }

  public void removeConnectedPlayer(Player player) {
    if (connectedPlayers.contains(player)) {
      connectedPlayers.remove(player);
    }
  }

  public void tick(float delta) {
    localPlayer.integrate(delta);
    
  }

  public void draw(SpriteBatch sb) {
    world.drawFloor(sb);
    for (Player p : connectedPlayers) {
      p.draw(sb);
    }
    localPlayer.draw(sb);
    world.drawWalls(sb);
  }

  public void start(World.WorldType type) {
    world = WorldGenerator.generateWorld(type);
    localPlayer.pos.set(world.spawn);
    for(Player p : connectedPlayers) {
      p.pos.set(world.spawn);
    }
  }
}
