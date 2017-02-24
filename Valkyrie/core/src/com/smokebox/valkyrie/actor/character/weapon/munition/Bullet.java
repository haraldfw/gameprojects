package com.smokebox.valkyrie.actor.character.weapon.munition;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.lib.utils.geom.Circle;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.ability.DamageReceiver;
import com.smokebox.valkyrie.actor.ability.Movable;
import com.smokebox.valkyrie.actor.character.weapon.GeneralWeapon;
import com.smokebox.valkyrie.trigger.DamageTrigger;

/**
 * Created by Harald Wilhelmsen on 10/3/2014.
 */
public class Bullet extends GeneralMunition {

	private DamageTrigger trigger;
	private boolean alive = true;

	Game game;

	Vector2 pos;
	Vector2 vel;

	private static Sprite bulletSprite;

	public Bullet(Vector2 pos, Vector2 vel, GeneralWeapon weapon, Game game) {
		this.game = game;
		this.pos = pos;
		this.vel = vel;
		//game.addDamageTrigger(new Circle(pos.x, pos.y, 0.25f), weapon.user.getDmgMul(), weapon.user);
	}

	/**
	 *
	 * @param delta     Time since last frame
	 * @return	Whether projectile is alive or should be removed.
	 */
	@Override
	public boolean update(float delta) {
		if(alive) {
			pos.addScaledVector(vel, delta);
			if(game.currentFloor.collidesWithWorld(pos.x, pos.y, 0.2f)) return false;
			return true;
		}
		// else
		game.removeDamageTrigger(trigger);
		return false;
	}

	@Override
	public void draw(SpriteBatch sb) {
		bulletSprite.setPosition(pos.x, pos.y);
		bulletSprite.draw(sb);
	}

	@Override
	public void onHit(Movable mv, DamageReceiver dmgRec) {
		mv.addImpulse(new Vector2(this.vel));
	}

	public static void loadAssets(AssetManager astmng) {
		astmng.load("data/bullet.png", Texture.class);
	}

	public static void onLoad(Game game) {
		bulletSprite = new Sprite((Texture)game.getAssetManager().get("data/bullet.png"));
		bulletSprite.setBounds(0, 0, bulletSprite.getRegionWidth()*Game.pixelSize, bulletSprite.getRegionHeight()*Game.pixelSize);
	}
}
