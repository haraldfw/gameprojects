package com.polarbirds.wendigo.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.wendigo.UserData;
import com.polarbirds.wendigo.world.model.Rock;
import com.polarbirds.wendigo.world.model.Tile;

/**
 * Created by Harald on 30.08.2016.
 */
public class WendigoWorld implements ContactFilter {

    private Tile[][] tiles;

    private World box2dWorld;

    public WendigoWorld() {
        box2dWorld = new World(new Vector2(), false);
        tiles = new Tile[10][10];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Rock(i, j);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (Tile[] tar : tiles) {
            for (Tile t : tar) {
                t.draw(batch);
            }
        }
    }

    @Override
    public boolean shouldCollide(Fixture a, Fixture b) {
        UserData userDataA = (UserData) a.getUserData();
        UserData userDataB = (UserData) b.getUserData();
        return userDataA.getCollisionGroup().doesContact(userDataB.getCollisionGroup());
    }
}
