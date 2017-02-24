/**
 * 
 */
package com.smokebox.kraken.weaponry;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.smokebox.kraken.Game;
import com.smokebox.kraken.character.Wielding;
import com.smokebox.kraken.effect.ParticleBurst_Shoot;
import com.smokebox.kraken.weaponry.ammunition.Grenade;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.lib.utils.geom.Polygon;

/**
 * @author Harald Floor Wilhelmsen
 *
 */
public class GrenadeLauncher extends Weapon {
	
	private Game game;
	
	float s = 0.2f;
	float ax = s*3;
	float ay = 0;
	private Polygon shape = new Polygon(new Vector2[]{
			new Vector2(-0.5f*s + ax, -0.5f*s + ay),
			new Vector2(-0.5f*s + ax, 0.5f*s + ay),
			new Vector2(2*s + ax, 0.5f*s + ay),
			new Vector2(2*s + ax, -0.5f*s + ay),
	});
	
	private float muzzleLength = 2*s + ax;
	
	public Sprite getIcon() {
		return null;
	}
	
	private final float expl_radius;
	private final float expl_force;
	private final float expl_damage;
	
	public GrenadeLauncher(Element xmlWep, Game game) {
		super(xmlWep);
		
		this.game = game;
		shape.origin.set(0, 0);
		
		expl_radius = Game.getStatByName("explosionRadius", xmlWep);
		expl_force = Game.getStatByName("explosionForce", xmlWep);
		expl_damage = Game.getStatByName("explosionDamage", xmlWep);
	}
	
	@Override
	public void draw(Vector2 pos, float rotation, ShapeRenderer sr) {
		sr.setColor(0.9f, 0.9f, 0.9f, 1);
		shape.setRotation(rotation);
		sr.polygon(shape.getVerticesAsFloatArray(pos.x, pos.y));
	}
	
	@Override
	public void attack(Vector2 pos, Vector2 direction, Wielding wielder) {
		Vector2 at = new Vector2(pos).addScaledVector(direction, muzzleLength);
		game.addProjectile(
				new Grenade(
						at,
						new Vector2(direction).add(
								new Vector2(
									-direction.y, direction.x
								).scl(
									(float) ((1 - wielder.getActiveAccuracy())*(Math.random()*2 - 1)))
								).nor().scl(wielder.getActiveProjectileSpeed()), 
						1/proj_mass, 
						proj_radius,
						wielder.getActiveRange(),
						wielder.getTeam(),
						game, 
						expl_radius, 
						expl_force, 
						expl_damage
						)
				);
		game.addAnimation(new ParticleBurst_Shoot(at, direction, 5));
		Vector2 kickVec = new Vector2(direction).nor().scl(wep_kick);
		game.shakeScreen(kickVec);
		wielder.addForce(kickVec.scl(500).flip());
		game.setMouseLock(!wep_automatic);
	}
}
