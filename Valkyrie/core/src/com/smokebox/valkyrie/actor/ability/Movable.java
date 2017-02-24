package com.smokebox.valkyrie.actor.ability;

import com.smokebox.lib.utils.Vector2;

public interface Movable {

	public float getInverseMass();
	public float frictionConstant();
	
	public Vector2 getPos();
	public Vector2 getVel();
	public Vector2 getAcc();
	public Vector2 getForceAccumulation();

	public void update(float delta);
	
	public default void addForce(Vector2 force) {
		getForceAccumulation().add(force);
	}
    public default void addImpulse(Vector2 impulse) {
		getVel().addScaledVector(impulse, getInverseMass());
	}
	public boolean grounded();
}
