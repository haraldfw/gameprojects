package com.polarbirds.wendigo.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.wendigo.graphics.model.Drawable;
import com.polarbirds.wendigo.phys.model.Collidable;

/**
 * Created by Harald on 01.09.2016.
 */
abstract class Player implements Drawable, Collidable {

    protected Body body;

    public Player(World world, BodyDef bodyDef) {
        body = world.createBody(bodyDef);
    }
}
