package com.smokebox.valkyrie.actor.specialEffects.lightning;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smokebox.valkyrie.Game;
import com.wilhelmsen.gamelib.utils.Vector2;

/**
 * Created by Harald Wilhelmsen on 8/21/2014.
 */
class LightningSegment {

    public final Vector2 from;
    public final Vector2 diff;
    public final float rotation;
    private final float width;
    private LightningHandler handler;

    public LightningSegment(Vector2 from, Vector2 to, LightningHandler handler) {
        this.from = from;
        diff = new Vector2(to).sub(from);
        width = diff.getMag();
        rotation = (float) Math.toDegrees(diff.getAngleAsRadians());

        this.handler = handler;
    }

    public void draw(SpriteBatch sb) {
        Sprite sprite = handler.sMid;
        float texShift = handler.texShift;
        sprite.setX(from.x);
        sprite.setY(from.y + texShift);
        sprite.setRotation(rotation);
        // sMid.setColor(color); // TODO add colors
        sprite.setSize(width, Game.pixelSize * 7f);

        sprite.draw(sb);
    }

    public void shapeDraw(ShapeRenderer sr) {
        sr.line(from.x, from.y, from.x + diff.x, from.y + diff.y);
    }
}
