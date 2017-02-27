package com.smokebox.valkyrie.module.movement;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.ability.Movable;

public class ME_ForceIntegration implements MovementExecutionModule {

	public static float gravitationConstant;
	public static float groundDamping;
	public static float airDamping;

	public ME_ForceIntegration(Element root) {
		Element phys = Game.getOwnerByName("physics", root);
		gravitationConstant = Game.getStatByName("gravitationAcc", phys);
		groundDamping = Game.getStatByName("groundDamping", phys);
		airDamping = Game.getStatByName("airDamping", phys);
	}

	public void update(Movable c, float delta) {
		Vector2 pos = c.getPos();
		Vector2 vel = c.getVel();
		Vector2 acc = c.getAcc();
		Vector2 forceAcm = c.getForceAccumulation();

		vel.addScaledVector(acc, delta);
		pos.addScaledVector(vel, delta);
		acc.set(0, -gravitationConstant);
		
		float im = c.getInverseMass();
		//if(im <= 0) return; // If mass is infinite, do not continue
		
		acc.addScaledVector(forceAcm, im);
		forceAcm.clear();
		float vx = Math.abs(vel.x);
		c.addImpulse(new Vector2(-Math.signum(vel.x) * Math.abs(vx * vx * c.frictionConstant() + 5 * vx * (c.grounded() ? groundDamping : airDamping))
										, -vel.y * airDamping).scl(delta));
		// Nullify value if insignificant. Reason being very small values cause heavier calculations.
		if(vx < 0.01f) vel.x = 0;
	}
}
