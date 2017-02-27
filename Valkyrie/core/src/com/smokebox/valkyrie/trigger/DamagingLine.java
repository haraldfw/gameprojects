package com.smokebox.valkyrie.trigger;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wilhelmsen.gamelib.utils.Intersect;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.wilhelmsen.gamelib.utils.geom.Circle;
import com.wilhelmsen.gamelib.utils.geom.Line;
import com.wilhelmsen.gamelib.utils.geom.Rectangle;
import com.smokebox.valkyrie.actor.ability.DamageDealer;

public class DamagingLine extends DamageTrigger {
	
	public Line line;
	float duration;

	public DamagingLine(Vector2 from, Vector2 to, float dmgMul, float duration, DamageDealer owner) {
		if(from.x > to.x) line = new Line(to, from);
			else line = new Line(from, to);
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
		return line.getMinimumDistance(c.x, c.y).getMag2() <= c.radius*c.radius;
	}

	@Override
	public boolean rect(Rectangle r) {
		return Intersect.intersection(line, r);
	}

	@Override
	public void debugDraw(ShapeRenderer sr) {
		sr.line(line.x, line.y, line.x2, line.y2);
	}
}
