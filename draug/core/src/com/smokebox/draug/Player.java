package com.smokebox.draug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.smokebox.draug.world.ContactGroups;

/**
 * Created by Harald Wilhelmsen on 10/10/2014.
 */
public class Player {

	public Body body;
	public GameScreen screen;

	float walkStrength = 100;
	float turnStrength = 5;

	int particlesEvery = 4; //...frames
	int framesSinceLastParticle = 0;

	public Player(Vector2 pos, GameScreen screen) {
		this.screen = screen;

		BodyDef bodyDef = new BodyDef();
		body = screen.map.world.createBody(bodyDef);
		body.setType(BodyDef.BodyType.DynamicBody);

		CircleShape circle = new CircleShape();
		circle.setRadius(0.4f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 2f;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0f;

		Fixture fixture = body.createFixture(fixtureDef);
		fixture.setFilterData(ContactGroups.PLAYER.filter);
		fixture.setUserData(this);

		body.setLinearDamping(10);
		body.setAngularDamping(4);
	}

	public void update(float delta) {
		// apply steering force
		Vector2 f = steeringDirection().scl(walkStrength);
		body.applyForceToCenter(f, true);
		// apply force to rotate towards mouse

		if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			float goalAngle = screen.game.mousePos().sub(body.getPosition()).angleRad();
			float startAngle = (float)((body.getAngle())%(Math.PI*2));

			float diff = goalAngle - startAngle;

			while(diff < Math.PI) diff += Math.PI*2;
			do diff -= Math.PI*2;
			while(diff > Math.PI);

			body.applyTorque(diff*turnStrength, true);
		}
	}

	private float normalizedAngle(float angle) {
		float tp = (float)Math.PI*2f;
		if(angle > tp) return normalizedAngle(angle -= tp);
		if(angle < -tp) return normalizedAngle(angle += tp);
		return angle;
	}

	private Vector2 steeringDirection() {
		return new Vector2((Gdx.input.isKeyPressed(Input.Keys.A) ? -1 : 0) + (Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0),
						(Gdx.input.isKeyPressed(Input.Keys.S) ? -1 : 0) + (Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : 0));
	}
}
