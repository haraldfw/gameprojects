package com.smokebox.valkyrie.trigger;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smokebox.lib.utils.Intersect;
import com.smokebox.lib.utils.geom.Circle;
import com.smokebox.lib.utils.geom.Rectangle;
import com.smokebox.valkyrie.actor.ability.DamageDealer;

public class DamagingCircle extends DamageTrigger {
	
	public Circle circle;
	float duration;

	public DamagingCircle(Circle circle, float dmgMul, float duration, DamageDealer owner) {
		this.circle = circle;
		this.duration = duration;
		this.dmgMul = dmgMul;
		this.owner = owner;
	}

	public void updateRadius(float newRadius) {
		circle.radius = newRadius;
	}

	@Override
	public boolean isActive(float delta) {
		duration -= delta;
		return duration > 0;
	}

	@Override
	public boolean circle(Circle c) {
		return Intersect.intersection(circle, c);
	}

	@Override
	public boolean rect(Rectangle r) {
		return Intersect.rectCircle(circle, r);
	}

	@Override
	public void debugDraw(ShapeRenderer sr) {
		sr.circle(circle.x, circle.y, circle.radius);
	}


}
