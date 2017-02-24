package com.smokebox.draug.miniGame.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.smokebox.draug.miniGame.MiniGameScreen;
import com.smokebox.draug.miniGame.Ship;
import com.smokebox.draug.world.ContactGroups;

/**
 * Created by Harald Wilhelmsen on 10/29/2014.
 */
public class Bullet implements GeneralProjectile {

	public final Body body;

	private boolean active = true;
	private float damage = 1;

	public Bullet(MiniGameScreen game) {
		body = game.world.createBody(new BodyDef());
		body.setType(BodyDef.BodyType.DynamicBody);
		body.setBullet(true);
		CircleShape c = new CircleShape();
		c.setRadius(0.1f);
		body.createFixture(c, 0.1f);
	}

	@Override
	public void hit(Ship ship) {
		// if object is not a ship, return
		if(ship == null) return;
		short ordBullet = body.getFixtureList().get(0).getFilterData().groupIndex;
		short ordShip = ship.getBody().getFixtureList().get(0).getFilterData().groupIndex;
		if((ordBullet == ContactGroups.FRIENDLY_BULLET.ordinal() &&
					ordShip == ContactGroups.ENEMY.ordinal()) ||
				   (ordBullet == ContactGroups.ENEMY_BULLET.ordinal() &&
							ordShip == ContactGroups.PLAYER.ordinal())) {
			ship.hurt(damage);
		}
		setActive(false);
	}

	public void draw(SpriteBatch sb) {

	}

	public void init(ContactGroups group, Vector2 pos, Vector2 vel, float density, float radius, float damage) {
		body.setTransform(pos, 0);
		body.setLinearVelocity(vel);
		body.setUserData(this);
		Fixture f = body.getFixtureList().get(0);
		f.getShape().setRadius(radius);
		f.setFilterData(group.filter);
		f.setDensity(density);
		this.damage = damage;
	}

	public void reset() {
		body.setTransform(0, 0, 0);
		body.setLinearVelocity(0, 0);
		body.getFixtureList().get(0).getShape().setRadius(0);
	}

	@Override
	public boolean getActive() {
		return active;
	}

	@Override
	public void setActive(boolean flag) {
		active = flag;
	}

	@Override
	public Body getBody() {
		return body;
	}
}
