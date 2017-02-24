package com.smokebox.draug.miniGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.smokebox.draug.miniGame.raycastCallbacks.CanSeePlayer;
import com.smokebox.draug.world.ContactGroups;

/**
 * Created by Harald Wilhelmsen on 10/27/2014.
 */
public class EnemyShip extends Ship {

	MiniGameScreen game;

	private static final float turnThrust = 50f;
	private static final float forwardThrust = 50f;

	private static final float thrustForTargetAngle = (float)Math.PI/8f;
	private static final float shootAngle = 0.1f;

	public boolean canSeePlayer = false;

	RayCastCallback callBack;

	float health = 10;

	int particlesEvery = 4; //...frames
	int framesSinceLastParticle = 0;

	private float timeBetweenShots = 0.75f; // seconds
	private float timeSinceLastFire = timeBetweenShots;

	public EnemyShip(Vector2 pos, MiniGameScreen game) {
		super(game);
		this.game = game;
		Body body = createBody();
		body.setTransform(pos, 1);
		super.setBody(body);

		callBack = new CanSeePlayer(this, game);
	}

	private Body createBody() {

		BodyDef bodyDef = new BodyDef();
		body = game.world.createBody(bodyDef);
		body.setType(BodyDef.BodyType.DynamicBody);

		PolygonShape poly = new PolygonShape();
		float scale = 0.2f;
		poly.set(new float[]{
									-2 * scale, -2 * scale,
									-3*scale, 0*scale,
									-2 * scale, 2 * scale,
									2 * scale, 0 * scale
		});
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = poly;
		fixtureDef.density = 3f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.9f;

		Fixture fixture = body.createFixture(fixtureDef);
		fixture.setFilterData(ContactGroups.ENEMY.filter);
		body.setUserData(this);

		body.setLinearDamping(3);
		body.setAngularDamping(10);
		direction = new Vector2(1, 0);
		return body;
	}

	@Override
	public void update(float delta) {
		direction.setAngleRad(body.getAngle());
		timeSinceLastFire += delta;

		float angleToPlayer = Math.abs(MiniGameScreen.smallestAngleBetween(direction, new Vector2(game.player.getBody().getPosition()).sub(getBody().getPosition())));

		setCanSeePlayer(false);
		game.world.rayCast(callBack, body.getPosition(), new Vector2(game.player.getBody().getPosition()));

		if(angleToPlayer < thrustForTargetAngle && canSeePlayer) {
			boolean effect = framesSinceLastParticle++ == particlesEvery;
			if(effect) {
				framesSinceLastParticle = 0;
			}
			thrust(ContactGroups.ENEMY_PARTICLE, forwardThrust, effect);
		}

		if(canSeePlayer && angleToPlayer < shootAngle && timeSinceLastFire > timeBetweenShots) {
			game.createBullet(ContactGroups.ENEMY_BULLET, body.getPosition(), new Vector2(direction).scl(25f), 1f, 0.2f, 1);
			timeSinceLastFire = 0f;
		} else if(canSeePlayer) {
			turnTowards(game.player.body.getPosition());
		}
	}

	@Override
	public void draw(SpriteBatch sb) {

	}

	public void setCanSeePlayer(boolean b) {
		canSeePlayer = b;
	}

	@Override
	public boolean getStatus() {
		return health > 0;
	}

	private void turnTowards(Vector2 point) {
		turnTowards(new Vector2(point).sub(body.getPosition()).angleRad());
	}

	private void turnTowards(float targetAngle) {
		body.applyTorque(
								(
										MiniGameScreen.smallestAngleBetween(
															direction,
															new Vector2(game.player.getBody().getPosition())
																.sub(this.body.getPosition())
										)/((float)Math.PI*2f))
										*turnThrust, true);
	}

	private Vector2 getThrusterPos() {
		return body.getWorldPoint(new Vector2(-0.6f, 0));
	}

	@Override
	public void hurt(float amount) {
		health -= amount;
	}

	@Override
	public void die() {
		game.addExplosions(body.getPosition(), 31);
	}
}
