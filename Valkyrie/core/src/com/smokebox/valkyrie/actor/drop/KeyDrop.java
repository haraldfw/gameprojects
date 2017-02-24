package com.smokebox.valkyrie.actor.drop;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smokebox.lib.utils.Intersect;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.lib.utils.geom.Rectangle;
import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;

/**
 * Created by Harald Wilhelmsen on 9/29/2014.
 */
public class KeyDrop extends GeneralDrop {

	private Rectangle rect;

	private DropHandler handler;

	private float animTimer = 0;

	public KeyDrop(Vector2 pos, Vector2 vel, DropHandler handler) {
		super(pos, vel, 1/0.1f, handler.move, handler.keyAnim, handler);
		rect = new Rectangle(pos.x, pos.y, 3f*handler.game.pixelSize, 7f*handler.game.pixelSize);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void debugDraw(ShapeRenderer sr) {
		sr.rect(rect.x, rect.y, rect.width, rect.height);
	}

	@Override
	public boolean collides(Rectangle rect) {
		return Intersect.intersection(this.rect, rect);
	}

	@Override
	public Rectangle getBoundingBox() {
		return rect;
	}

	@Override
	public void setAnimTimer(float animTimer) {
		this.animTimer = animTimer;
	}

	@Override
	public void onPickup(PlayableCharacter picker) {
		picker.increaseKeys(1);
	}
}
