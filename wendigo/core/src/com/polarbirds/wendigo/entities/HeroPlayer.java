package com.polarbirds.wendigo.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.wendigo.phys.CollisionGroup;

/**
 * Created by Harald on 01.09.2016.
 */
public class HeroPlayer extends Player {

    private CollisionGroup collisionGroup;

    public HeroPlayer(World world, Vector2 pos) {
        super(world, createBodyDef(pos));
        collisionGroup = CollisionGroup.PLAYER_NORMAL;
    }

    private static BodyDef createBodyDef(Vector2 pos) {
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setPosition(pos);
        circleShape.setRadius(0.5f);
        fixtureDef.shape = circleShape;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // // FIXME: 01.09.2016
        return bodyDef;
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public CollisionGroup getCollisionGroup() {
        return collisionGroup;
    }
}
