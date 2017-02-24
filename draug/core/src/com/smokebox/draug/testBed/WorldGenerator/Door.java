package com.smokebox.draug.testBed.WorldGenerator;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Harald Wilhelmsen on 28. Nov 2014.
 */
public class Door {

	public final Door leadsTo;

	public final Body body;

	public Door(float x, float y, float width, float height, Door leadsTo, World world) {
		this.leadsTo = leadsTo;

		FixtureDef rect = new FixtureDef();
		PolygonShape p = new PolygonShape();
		float hw = width/2;
		float hh = height/2;
		p.set(new float[]{		x - hw, y - hh,
								x - hw, y + hh,
								x + hw, y + hh,
								x + hw, y - hh});
		rect.shape = p;

		BodyDef b = new BodyDef();
		b.type = BodyDef.BodyType.StaticBody;
		body = world.createBody(b);
		body.createFixture(rect);
	}
}
