package com.smokebox.valkyrie.actor.character.enemy;

import com.smokebox.lib.utils.Vector2;
import com.smokebox.lib.utils.geom.Circle;
import com.smokebox.lib.utils.geom.Line;
import com.smokebox.lib.utils.geom.Rectangle;
import com.smokebox.valkyrie.actor.ability.DamageReceiver;
import com.smokebox.valkyrie.actor.character.GeneralCharacter;

/**
 * Created by Harald Wilhelmsen on 10/2/2014.
 */
public abstract class GeneralEnemy extends GeneralCharacter {

	public GeneralEnemy() {
		super(null, null, null, null, null, null);

	}

	@Override
	public Rectangle getBoundingBox() {
		return null;
	}

	@Override
	public void dealDamage(DamageReceiver rec, float multiplier) {
		rec.receiveDamage(multiplier);
	}

	@Override
	public Team getTeam() {
		return Team.ENEMY;
	}

	@Override
	public void receiveDamage(float amount) {

	}

	@Override
	public boolean col_line(Line l) {
		return false;
	}

	@Override
	public boolean col_circle(Circle c) {
		return false;
	}

	@Override
	public boolean col_Rect(Rectangle r) {
		return false;
	}

	@Override
	public float getHealth() {
		return 0;
	}

	@Override
	public Vector2 getWeaponMuzzle() {
		return null;
	}

	@Override
	public boolean weaponIsAvailable() {
		return false;
	}

	@Override
	public Vector2 getAttackRootCoordinates() {
		return null;
	}
}