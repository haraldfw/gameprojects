package com.smokebox.draug.miniGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.smokebox.draug.world.ContactGroups;

/**
 * Created by Harald Wilhelmsen on 10/27/2014.
 */
public class PlayerShip extends Ship {

	Body body;

	private static float turnThrust = 1.5f;
	private static float forwardThrust = 75f;

	public static float health = 10;

	private final MiniGameScreen game;

	private final static int particlesEvery = 3; //...frames
	private int framesSinceLastParticle = 0;

	private float timeBetweenShots = 0.25f; // seconds
	private float timeSinceLastFire = timeBetweenShots;

	public PlayerShip(MiniGameScreen game) {
		super(game);
		this.game = game;
		super.setBody(createBody());
	}

	private Body createBody() {
		BodyDef bodyDef = new BodyDef();
		body = game.world.createBody(bodyDef);
		body.setUserData(this);
		body.setType(BodyDef.BodyType.DynamicBody);

		PolygonShape pol = new PolygonShape();
		float scale = 0.2f;
		pol.set(new float[]{
								   -2*scale, -1.8f*scale,
								   -2*scale, 1.8f*scale,
								   2*scale, 0.5f*scale,
								   2*scale, -0.5f*scale,
		});

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = pol;
		fixtureDef.density = 3f;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0.9f;

		Fixture fixture = body.createFixture(fixtureDef);
		fixture.setFilterData(ContactGroups.PLAYER.filter);

		body.setLinearDamping(2);
		body.setAngularDamping(4);
		return body;
	}

	@Override
	public void update(float delta) {
		direction.setAngleRad(body.getAngle());
		timeSinceLastFire += delta;
		float turnDir = 0;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) turnDir += turnThrust;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) turnDir -= turnThrust;
		if(turnDir != 0) body.applyTorque(turnDir, true);

		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			boolean effect = framesSinceLastParticle++ == particlesEvery;
			if(effect) {
				framesSinceLastParticle = 0;
			}
			thrust(ContactGroups.FRIENDLY_PARTICLE, forwardThrust, effect);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && timeSinceLastFire > timeBetweenShots) {
			game.createBullet(ContactGroups.FRIENDLY_BULLET, body.getPosition(), new Vector2(direction).scl(50f), 2f, 0.25f, 20);
			timeSinceLastFire = 0;
		}
	}

	private Vector2 getThrusterPos() {
		return body.getWorldPoint(new Vector2(-0.5f, 0));
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

	@Override
	public void hurt(float amount) {
		health -= amount;
	}

	@Override
	public void die() {
		game.addExplosions(body.getPosition(), 314);
	}
}
