package com.polarbirds.wendigo.world.model;

import com.badlogic.gdx.graphics.Texture;
import com.polarbirds.wendigo.phys.CollisionGroup;

/**
 * Created by Harald on 01.09.2016.
 */
public class Rock extends Tile {

    private static Texture texture = new Texture("data/tile_0.png");

    public Rock(float posx, float posy) {
        super(posx, posy);
    }

    @Override
    protected Texture getTexture() {
        return texture;
    }

    @Override
    public CollisionGroup getCollisionGroup() {
        return CollisionGroup.TILE_SOLID;
    }
}
