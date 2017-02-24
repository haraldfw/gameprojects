package com.smokebox.draug;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.smokebox.draug.world.ContactGroups;

/**
 * Created by Harald Wilhelmsen on 10/11/2014.
 */
public class Bolt {

	public boolean hasStuck = false;

	/**
	 * Array for collisions with entities.
	 */
	public Bolt(Vector2 pos, Vector2 vel, GameScreen screen) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		PolygonShape shape = new PolygonShape();

		float scl = 0.5f;
		shape.set(new float[]{	-1.4f * scl, 0,
									 0, 0.1f * scl,
									 0.5f * scl, 0,
									 0, -0.1f * scl });

		Body body = screen.map.world.createBody(new BodyDef());
		body.setType(BodyDef.BodyType.DynamicBody);
		Fixture f = body.createFixture(shape, 1);
		f.setFilterData(ContactGroups.FRIENDLY_BOLT.filter);
		body.setTransform(pos, vel.angleRad());
		body.setLinearVelocity(vel.scl(10));
		body.setUserData(this);
	}
}
