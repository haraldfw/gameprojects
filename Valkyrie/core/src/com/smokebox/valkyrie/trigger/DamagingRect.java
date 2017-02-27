package com.smokebox.valkyrie.trigger;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wilhelmsen.gamelib.utils.Intersect;
import com.wilhelmsen.gamelib.utils.geom.Circle;
import com.wilhelmsen.gamelib.utils.geom.Rectangle;
import com.smokebox.valkyrie.actor.ability.DamageDealer;

public class DamagingRect extends DamageTrigger {
	
	public Rectangle rect;
	float duration = 0;

	/**
	 * Creates a new DamageRect.
	 * @param rect	DamageCollision
	 * @param dmgMul	DamageMultiplier
	 * @param duration	How long it lasts in seconds
	 * @param owner	The damage dealer, typically the creator
	 */
	public DamagingRect(Rectangle rect, float dmgMul, float duration, DamageDealer owner) {
		rect.ensureAxisAlignment();
		this.rect = rect;
		this.duration = duration;
		this.dmgMul = dmgMul;
		this.owner = owner;
	}

	@Override
	public boolean isActive(float delta) {
		duration -= delta;
		return duration > 0;
	}

	@Override
	public boolean circle(Circle c) {
		return Intersect.rectCircle(c, rect);
	}

	@Override
	public boolean rect(Rectangle r) {
		return Intersect.intersection(rect, r);
	}

	@Override
	public void debugDraw(ShapeRenderer sr) {
		sr.rect(rect.x, rect.y, rect.width, rect.height);
	}
}
