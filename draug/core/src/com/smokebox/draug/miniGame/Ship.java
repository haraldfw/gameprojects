package com.smokebox.draug.miniGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.smokebox.draug.world.ContactGroups;

/**
 * Created by Harald Wilhelmsen on 10/28/2014.
 */
public abstract class Ship implements MovableEntity {

	Body body;
	Vector2 direction;

	MiniGameScreen game;

	public Ship(MiniGameScreen game) {
		this.game =game;
		direction = new Vector2(1, 0);
	}

	private Vector2 getThrusterPos() {
		return new Vector2(body.getPosition()).add(new Vector2(1, 0).rotateRad(body.getAngle()).scl(-0.5f));
	}

	void setBody(Body body) {
		this.body = body;
	}

	@Override
	public void draw(SpriteBatch sb) {

	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public boolean getStatus() {
		return true;
	}

	void thrust(ContactGroups groupOfParticle, float forwardThrust, boolean effect) {
		Vector2 f = new Vector2(direction).scl(forwardThrust);
		body.applyForceToCenter(f, true);
		if(effect) game.thrustEffect(groupOfParticle, getThrusterPos(), direction, body.getMass() * 2, 0.2f);
	}

	public abstract void hurt(float amount);

	public abstract void die();
}
