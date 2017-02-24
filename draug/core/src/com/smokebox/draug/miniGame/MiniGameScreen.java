package com.smokebox.draug.miniGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Pool;
import com.smokebox.draug.DraugGame;
import com.smokebox.draug.miniGame.projectiles.Bullet;
import com.smokebox.draug.miniGame.projectiles.GeneralProjectile;
import com.smokebox.draug.world.ContactGroups;
import com.smokebox.draug.world.MyContactListener;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 10/10/2014.
 */
public class MiniGameScreen extends ScreenAdapter {

	public DraugGame game;

	public Box2DDebugRenderer debugRenderer;

	public World world;

	private class MyContactFilter implements ContactFilter {
		@Override
		public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
			return ContactGroups.matrix[fixtureA.getFilterData().groupIndex][fixtureB.getFilterData().groupIndex];
		}
	}
	public PlayerShip player;

	MyContactListener contactListener;

	private ArrayList<Ship> ships;

	float timePassed = 0;

	Pool<Particle> particlePool;
	ArrayList<Particle> activeParticles;

	private ArrayList<GeneralProjectile> activeProjectiles;

	public MiniGameScreen(DraugGame game) {
		this.game = game;

		world = new World(new Vector2(), false);
		world.setContactFilter(new MyContactFilter());
		world.setContactListener(contactListener = new MyContactListener(world));

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.fixedRotation = true;
		bodyDef.awake = true;
		Body worldBody = world.createBody(bodyDef);
		createBoxWorld(worldBody);

		ships = new ArrayList<Ship>();

		particlePool = new Pool<Particle>() {
			@Override
			protected Particle newObject() {
				return new Particle();
			}
		};
		activeParticles = new ArrayList<Particle>();

		activeProjectiles = new ArrayList<GeneralProjectile>();


		debugRenderer = new Box2DDebugRenderer(
													  true,	// bodies
													  false,	// joints
													  false,	// AABBs
													  true,		// inactives
													  false,	// velocities
													  false);	//	contacts

		startNewGame();
	}

	public void createBullet(ContactGroups contactGroup, Vector2 startPos, Vector2 vel, float density, float radius, float damage) {
		Bullet b = new Bullet(this);
		b.init(contactGroup, startPos, new Vector2(vel), density, radius, damage);
		activeProjectiles.add(b);
	}

	public void createBoxWorld(Body worldBody) {
		FixtureDef fix;
		EdgeShape e;
		Fixture fixture;

		float w = game.cam.viewportWidth;
		float h = game.cam.viewportHeight;


		fix = new FixtureDef();
		e = new EdgeShape();
		e.set(0, 0, w, 0);
		fix.shape = e;
		fix.density = 1;
		fix.isSensor = false;
		worldBody.createFixture(fix).setFilterData(ContactGroups.WORLD.filter);

		fix = new FixtureDef();
		e = new EdgeShape();
		e.set(w, 0, w, h);
		fix.shape = e;
		fix.density = 1;
		fix.isSensor = false;
		worldBody.createFixture(fix).setFilterData(ContactGroups.WORLD.filter);

		fix = new FixtureDef();
		e = new EdgeShape();
		e.set(0, h, w, h);
		fix.shape = e;
		fix.density = 1;
		fix.isSensor = false;
		worldBody.createFixture(fix).setFilterData(ContactGroups.WORLD.filter);

		fix = new FixtureDef();
		e = new EdgeShape();
		e.set(0, 0, 0, h);
		fix.shape = e;
		fix.density = 1;
		fix.isSensor = false;
		worldBody.createFixture(fix).setFilterData(ContactGroups.WORLD.filter);
	}

	public void startNewGame() {
		ships.clear();
		player = new PlayerShip(this);
		player.getBody().setTransform(game.cam.viewportWidth/2, game.cam.viewportHeight/2, 0);
		ships.add(player);
	}

	@Override
	public void render(float delta) {
		world.step(delta, 6, 3);

		for(int i = 0; i < ships.size();) {
			Ship e = ships.get(i);
			e.update(delta);
			if(!e.getStatus()) {
				ships.remove(i);
				e.die();
				world.destroyBody(e.getBody());
			} else {
				i++;
			}
		}

		for(int i = 0; i < activeParticles.size();) {
			Particle p = activeParticles.get(i);
			if(!p.update(delta)) {
				activeParticles.remove(i);
				particlePool.free(p);
			} else {
				i++;
			}
		}

		for(int i = 0; i < activeProjectiles.size();) {
			GeneralProjectile b = activeProjectiles.get(i);
			if(b.getActive()) {
				i++;
			} else {
				world.destroyBody(b.getBody());
				activeProjectiles.remove(i);
			}
		}

		Vector3 vec = game.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		if(Gdx.input.isKeyPressed(Input.Keys.O)) ships.add(new EnemyShip(new Vector2(vec.x, vec.y), this));

		debugRenderer.render(world, game.cam.combined);

		timePassed += delta;
	}

	public void thrustEffect(ContactGroups group, Vector2 pos, Vector2 dir, float shift, float radius) {
		dir = new Vector2(-dir.x, -dir.y);
		Particle p = particlePool.obtain();
		p.init(group, new Vector2(pos), dir.scl(75f), radius, 0.75f);
		activeParticles.add(p);
	}

	public static float smallestAngleBetween(Vector2 source, Vector2 target) {
		return smallestAngleBetween(source.angleRad(), target.angleRad());
	}

	public static float smallestAngleBetween(float sourceAngle, float targetAngle) {
		float a = targetAngle - sourceAngle;
		float pi = (float)Math.PI;
		a += (a>pi) ? -2*pi : (a<-pi) ? 2*pi : 0;
		return a;
	}

	public void addExplosions(Vector2 pos, float strength) {
		for(float f = 0; f < strength; f++) {
			createBullet(ContactGroups.FRIENDLY_BULLET, pos, new Vector2((float)Math.cos(f)*20f, (float) Math.sin(f)*20f), 10f, 0.25f, 10f);
		}
	}


	private class Particle implements Pool.Poolable {

		public Body body;

		private float[] color;

		private float timeAlive;

		public Particle() {
			body = world.createBody(new BodyDef());
			body.setType(BodyDef.BodyType.DynamicBody);
			CircleShape c = new CircleShape();
			c.setRadius(0.1f);
			body.createFixture(c, 0.1f);
		}

		public void init(ContactGroups group, Vector2 pos, Vector2 vel, float radius, float aliveFor) {
			body.setTransform(pos, 0);
			body.setLinearVelocity(vel.scl(0.1f));
			timeAlive = aliveFor;
			Fixture f = body.getFixtureList().get(0);
			f.getShape().setRadius(radius);
			f.setFilterData(group.filter);
			body.setActive(true);
		}

		public boolean update(float delta) {
			Shape s = body.getFixtureList().get(0).getShape();
			s.setRadius(s.getRadius() * 0.9f);
			timeAlive -= delta;
			return (timeAlive -= delta) > 0;
		}

		public void draw(SpriteBatch sb) {

		}

		@Override
		public void reset() {
			body.setTransform(0, 0, 0);
			body.setLinearVelocity(0, 0);
			timeAlive = 0;
			body.getFixtureList().get(0).getShape().setRadius(0);
			body.setActive(false);
		}
	}
}
