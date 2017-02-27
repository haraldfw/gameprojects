package com.smokebox.valkyrie.actor.character.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.XmlReader;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.character.GeneralCharacter;
import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;

/**
 * Created by Harald Wilhelmsen on 10/2/2014.
 */
public abstract class GeneralWeapon {

	public static enum WeaponType {
		PISTOL,
		RIFLE,
		LAUNCHER
	}

	Animation[] animations;
	Vector2 handle;
	Vector2 muzzle;
	boolean readyToFire = true;

	private float animTimer = 0;

	public PlayableCharacter user;

	public Game game;

	/**
	 * @param type	The weaponType
	 * @param handle	The difference, in pixels, from where the type-standardized hold-point is for the weapon to where the handle is on the sprite
	 * @param muzzle	The difference, in pixels, from the bottom-left of the gunSprite, to the muzzle point
	 */
	public GeneralWeapon(WeaponType type,
						 Vector2 handle, Vector2 muzzle, PlayableCharacter user, Game game) {
		this.user = user;
		this.game  = game;
		this.handle = handle;
		this.muzzle = muzzle;
	}

	public void update(float delta) {
		// increment animTimer by delta
		// if active and past activationTime reset timer and set readyToFire to true
		if((animTimer += delta) > getActivationTime() + getTimeBetweenShots() && !readyToFire) {
			animTimer = 0;
			readyToFire = true;
		}
		animTimer += delta;
	}

	/**
	 * @param pos	The position of where the weapon-stock should be on the character.
	 * @param sb	SpriteBatch
	 */
	public void draw(Vector2 pos, SpriteBatch sb) {
		Vector2 p = new Vector2(pos).add(handle);
		float angle = (float)Math.toDegrees(user.getAimVector2().getAngleAsRadians());
		TextureRegion r = animations[readyToFire ? 0 : 1].getKeyFrame(animTimer, true);
		sb.draw(r, p.x, p.y, 0, 0, r.getRegionWidth()*game.pixelSize, r.getRegionHeight()*game.pixelSize, 1, 1, angle);
	}

	public void setAnimTimer(float f) {
		this.animTimer = f;
	}

	public abstract void attack();

	public boolean isReadyToFire() {
		return readyToFire;
	}

	/**
	 * @param animations	Array of animations for the weapon in the form {Idle, Shoot}
	 */
	public void setAnimations(Animation[] animations) {
		this.animations = animations;
	}

	public abstract float getActivationTime();
	public abstract float getTimeBetweenShots();
}

