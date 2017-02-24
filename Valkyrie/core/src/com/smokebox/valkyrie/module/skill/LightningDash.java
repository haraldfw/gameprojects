package com.smokebox.valkyrie.module.skill;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.lib.utils.geom.Rectangle;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.ability.DamageDealer;
import com.smokebox.valkyrie.actor.ability.Movable;
import com.smokebox.valkyrie.actor.ability.SkillUser;
import com.smokebox.valkyrie.actor.character.GeneralCharacter;
import com.smokebox.valkyrie.module.activation.ActivationModule;

public class LightningDash implements SkillModule {

	private Game game;
	private ActivationModule activation;

	private static float damageMultiplier = 0;
	private static float skillTime = 0; // a scalar affecting how long the user dashes
	private static float cooldown = 0; // cooldown-time
	private static float dashStrengthScale = 0;

	public GeneralCharacter user;
	private boolean active = false;
	private Vector2 dashStart = new Vector2();
	private int dirScale = 1;
	private float currDistanceToGround;
	
	private float cooldownTimer = 0;
	/* TODO change this effect from a teleport to an actual dash.
		Propelling the user forward for a short period of time.*/
	
	public LightningDash(Game game, ActivationModule activation, GeneralCharacter user, Element root) {
		this.game = game;
		this.activation = activation;
		this.user = user;
		initializeConstants(root);
	}

	private void initializeConstants(Element root) {
		Element owner = Game.getOwnerByName("lightningDash", root);
		damageMultiplier = Game.getStatByName("damageMultiplier", owner);
		skillTime = Game.getStatByName("skillTime", owner);
		cooldown = Game.getStatByName("cooldown", owner);
		dashStrengthScale = Game.getStatByName("dashStrengthScale", owner);
	}
	
	@Override
	public boolean update(float delta) {
		if(cooldownTimer > 0) cooldownTimer -= delta;
		if(!active && !user.areSkillsLocked() && activation.activate() && cooldownTimer <= 0) {
			// activate
			dirScale = user.lookingRight() ? 1 : -1;

			user.setSkillLockTime(skillTime);
			cooldownTimer = cooldown;

			active = true;

			currDistanceToGround = game.currentFloor.getDistanceToGround(user.getPos());
			dashStart.set(new Vector2(user.getPos()).add(0, -currDistanceToGround));
			return true;
		}
		if(active) {
			if((cooldown - cooldownTimer) > skillTime) {
				active = false;
				return false;
			} else {
				user.addForce(new Vector2(
												 dirScale
													* user.getMoveStrengthOnGround()
													* user.getInverseMass()
													* 10f * dashStrengthScale, 0));
				return true;
 			}
		}
		return false;
	}

	@Override
	public void draw(SpriteBatch sb) {
		if(active) {
			float yDisp = game.currentFloor.getDistanceToGround(user.getWeaponMuzzle());
			if(Math.abs(yDisp) < 1) yDisp = -1;
			Vector2 l = new Vector2(user.getWeaponMuzzle()).add(0, -yDisp);
			game.createLightningBolt(user.getPos(), l, 0.1f);
			game.addDamageTrigger(
										 new Rectangle(
															  dashStart.x, dashStart.y,
															  user.getWeaponMuzzle().x - dashStart.x, user.getWeaponMuzzle().y - dashStart.y
										 ).ensureAxisAlignment(), skillTime - (cooldown - cooldownTimer), damageMultiplier, user);
			dashStart.set(l);
		}
	}
}
