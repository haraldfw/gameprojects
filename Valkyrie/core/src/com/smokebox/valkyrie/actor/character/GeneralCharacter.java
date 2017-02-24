package com.smokebox.valkyrie.actor.character;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.lib.utils.geom.Rectangle;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.ability.*;
import com.smokebox.valkyrie.module.movement.MoveMotivationModule;
import com.smokebox.valkyrie.module.movement.MovementExecutionModule;
import com.smokebox.valkyrie.module.skill.SkillModule;

import java.util.ArrayList;

public abstract class GeneralCharacter implements Movable, Drawable,
				CollidesWorld, DamageDealer, DamageReceiver, SkillUser {

	public enum Team {
		PLAYER,
		NEUTRAL,
		ENEMY
	}
	public enum AnimationState {
		IDLE,
		MOVING,
		FALLING,
		ATTACKING
	}

	Game game;

	ArrayList<SkillModule> skills;

	AnimationState animState;
	float animTimer = 0;
	Animation[] animations;
	float skillLockTimer;
	boolean lookingRight;
	boolean grounded;

	MoveMotivationModule moveM;
	MovementExecutionModule moveE;

	protected Vector2 pos;
	private Vector2 vel;
	private Vector2 acc;
	private Vector2 forceAccumulation;

	private final float baseMaxHealth;
	private final float moveStrengthOnGround;
	private final float moveStrengthInAir;
	private final float jumpStrength;
	private final float inverseMass;
	private final float frictionConstant;

	public GeneralCharacter(Vector2 pos, MoveMotivationModule mm, MovementExecutionModule me,
							Element owner, Animation[] animations, Game game) {
		System.out.println("GeneralCharacter-constructor called. " + pos.x + ", " + pos.y);
		this.pos = new Vector2(pos);
		vel = new Vector2();
		acc = new Vector2();
		forceAccumulation = new Vector2();

		this.game = game;
		moveM = mm;
		moveE = me;

		baseMaxHealth = Game.getStatByName("baseMaxHealth", owner);
		moveStrengthOnGround = Game.getStatByName("moveStrengthOnGround", owner);
		moveStrengthInAir = Game.getStatByName("moveStrengthInAir", owner);
		jumpStrength = Game.getStatByName("jumpStrength", owner);
		inverseMass = Game.getStatByName("inverseMass", owner);
		frictionConstant = Game.getStatByName("frictionConstant", owner);
	}

	protected void setAnimations(Animation[] anims) {
		this.animations = anims;
	}

	protected void setSkills(ArrayList<SkillModule> skills) {
		this.skills = skills;
	}

	@Override
	public void update(float delta) {
		float dir = moveM.getMoveDirection();

		if(dir == 0) {
			animState = AnimationState.IDLE;
		} else {
			lookingRight = (dir >= 0);
			animState = AnimationState.MOVING;
			addForce(new Vector2(dir* getActiveMoveStrength(), 0));
		}
		moveE.update(this, delta);
		game.handleWorldCollisions(this);
		animTimer += delta;
	}

	@Override
	public void draw(SpriteBatch sb) {
		TextureRegion textureRegion = null;
		float xShift = 0;
		float yShift = 0;
		switch (animState) {
			case ATTACKING:

				break;
			case FALLING:

				break;
			case MOVING:
				textureRegion = new TextureRegion(animations[1].getKeyFrame(animTimer, true));
				textureRegion.flip(!lookingRight, false);
				break;

			default: // IDLE
				textureRegion = new TextureRegion(animations[0].getKeyFrame(animTimer, true));
				textureRegion.flip(!lookingRight, false);
				break;
		}
		sb.draw(textureRegion, pos.x + xShift, pos.y + yShift, textureRegion.getRegionWidth()*game.pixelSize, textureRegion.getRegionHeight()*game.pixelSize);
		for(SkillModule s : skills) s.draw(sb);
	}

	@Override
	public void debugDraw(ShapeRenderer sr) {
		Rectangle r = getBoundingBox();
		sr.rect(r.x, r.y, r.width, r.height);
	}

	@Override
	public void addForce(Vector2 force) {
		forceAccumulation.add(force);
	}

	@Override
	public void addImpulse(Vector2 impulse) {
		vel.addScaledVector(impulse, getInverseMass());
	}

	@Override
	public boolean grounded() {
		return grounded;
	}

	@Override
	public void setGrounded(boolean b) {
		grounded = b;
	}

	@Override
	public void updateSkills(float delta) {
		skillLockTimer -= delta;
		for(SkillModule s : skills) {
			s.update(delta);
		}
	}

	@Override
	public boolean areSkillsLocked() {
		return skillLockTimer > 0;
	}

	@Override
	public void setSkillLockTime(float f) {
		skillLockTimer = f;
	}

	@Override
	public boolean lookingRight() {
		return lookingRight;
	}

	public float getActiveMoveStrength() {
		return (grounded ? moveStrengthOnGround : moveStrengthInAir)*(sprinting() ? 2 : 1);
	}

	public float getMoveStrengthOnGround() {
		return moveStrengthOnGround;
	}

	public float getJumpStrength() {
		return jumpStrength;
	}

	@Override
	public float getInverseMass() {
		return inverseMass;
	}

	@Override
	public float frictionConstant() {
		return frictionConstant;
	}

	@Override
	public void setAnimTimer(float animTimer) {
		this.animTimer = animTimer;
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
		return forceAccumulation;
	}

	public static void loadAssets(AssetManager astmng) {
		System.out.println("loadAssets(...) called from class who has failed to override");
	}

	public boolean sprinting() {
		return false;
	}

}
