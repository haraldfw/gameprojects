package com.smokebox.valkyrie.actor.drop;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wilhelmsen.gamelib.utils.Intersect;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.wilhelmsen.gamelib.utils.geom.Rectangle;
import com.smokebox.valkyrie.actor.ability.CollidesWorld;
import com.smokebox.valkyrie.actor.ability.Drawable;
import com.smokebox.valkyrie.actor.ability.Movable;
import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;
import com.smokebox.valkyrie.module.movement.MovementExecutionModule;

/**
 * Created by Harald Wilhelmsen on 9/29/2014.
 */
public abstract class GeneralDrop implements Movable, CollidesWorld, Drawable {

	public boolean pickUpOnPlayerTouch = true;
	MovementExecutionModule movement;
	boolean grounded = false;

	Vector2 pos;
	Vector2 vel;
	Vector2 acc;
	Vector2 forceAcm;
	float inverseMass;

	Animation anim;
	float animTimer = 0;
	float frameTime = 0.1f;
	DropHandler handler;

	protected GeneralDrop(Vector2 pos, Vector2 vel, float inverseMass, MovementExecutionModule movement, Animation anim, DropHandler handler) {
		this.pos = pos;
		this.vel = vel;
		acc = new Vector2();
		forceAcm = new Vector2();
		this.inverseMass = inverseMass;
		this.movement = movement;
		this.anim = anim;
		this.handler = handler;
	}

	public boolean update(PlayableCharacter player, float delta) {
		if(animTimer > anim.getAnimationDuration()) animTimer -= anim.getAnimationDuration() + delta;
		else if(animTimer < frameTime) animTimer += delta/10f;
		else animTimer += delta;

		movement.update(this, delta);
		getBoundingBox().setPos(pos);

		if(!vel.isZero()) handler.game.currentFloor.worldCollision(this);

		if(Intersect.intersection(this.getBoundingBox(), player.getBoundingBox())) {
			this.onPickup(player);
			return false;
		}
		return true;
	}

	@Override
	public void draw(SpriteBatch sb) {
		TextureRegion r = anim.getKeyFrame(animTimer);
		sb.draw(r, pos.x, pos.y, r.getRegionWidth()*handler.game.pixelSize, r.getRegionHeight()*handler.game.pixelSize);
	}
	@Override
	public abstract void debugDraw(ShapeRenderer sr) ;

	@Override
	public void addForce(Vector2 force) {
		forceAcm.add(force);
	}

	@Override
	public void addImpulse(Vector2 impulse) {
		vel.add(impulse);
	}

	@Override
	public float getInverseMass() {
		return inverseMass;
	}

	@Override
	public float frictionConstant() {
		return 0;
	}

	@Override
	public Vector2 getPos() {
		return pos;
	}

	@Override
	public Vector2 getVel() {
		return vel;
	}

	@Override
	public Vector2 getAcc() {
		return acc;
	}

	@Override
	public Vector2 getForceAccumulation() {
		return forceAcm;
	}

	@Override
	public boolean grounded() {
		return grounded;
	}

	@Override
	public void setGrounded(boolean b) {
		grounded = b;
	}

	public abstract boolean collides(Rectangle rect);

	public abstract void onPickup(PlayableCharacter picker);
}
