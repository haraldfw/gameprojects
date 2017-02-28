package com.smokebox.valkyrie.actor.drop;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;
import com.wilhelmsen.gamelib.utils.Intersect;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.wilhelmsen.gamelib.utils.geom.Rectangle;

/**
 * Created by Harald Wilhelmsen on 10/2/2014.
 */
public class BombDrop extends GeneralDrop {
    private Rectangle rect;

    private DropHandler handler;

    private float animTimer = 0;

    public BombDrop(Vector2 pos, Vector2 vel, DropHandler handler) {
        super(pos, vel, 1 / 3f, handler.move, handler.bombAnim, handler);
        rect = new Rectangle(pos.x, pos.y, 9 * handler.game.pixelSize, 11f * handler.game.pixelSize);
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
        picker.increaseBombs(1);
    }
}
