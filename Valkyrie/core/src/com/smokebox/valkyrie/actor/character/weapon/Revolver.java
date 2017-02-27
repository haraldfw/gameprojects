package com.smokebox.valkyrie.actor.character.weapon;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.XmlReader;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.ability.Drawable;
import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;
import com.smokebox.valkyrie.actor.character.weapon.munition.Bullet;

/**
 * Created by Harald Wilhelmsen on 10/3/2014.
 */
public class Revolver extends GeneralWeapon {

	private static float activationTime;
	private static float timeBetweenShots;
	private static float projectileVel;
	private static float damage;


	public Revolver(PlayableCharacter user, XmlReader.Element root, Game game) {
		super(WeaponType.PISTOL,
					 new Vector2(), new Vector2(), user, game);
		initializeConstants(Game.getOwnerByName("revolver", root));
		Animation[] anims = new Animation[]{new Animation(0, Drawable.extractFrames(game.getAssetManager().get("data/revolver.png"), 10, 5)), null};
		setAnimations(anims);
	}

	public void initializeConstants(XmlReader.Element owner) {
		activationTime = Game.getStatByName("activationTime", owner);
		timeBetweenShots = Game.getStatByName("timeBetweenShots", owner);
		projectileVel = Game.getStatByName("projectileVel", owner);
	}

	public static void loadAssets(AssetManager astmng) {
		astmng.load("data/revolver.png", Texture.class);
	}

	@Override
	public void attack() {
		if(!readyToFire) return;
		// else
		Bullet b = new Bullet(user.getWeaponMuzzle(), user.getAimVector2().scl(projectileVel), this, game);
		game.addMunition(b);
	}

	@Override
	public float getActivationTime() {
		return activationTime;
	}

	@Override
	public float getTimeBetweenShots() {
		return timeBetweenShots;
	}
}
