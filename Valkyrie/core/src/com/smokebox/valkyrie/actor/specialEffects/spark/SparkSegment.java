package com.smokebox.valkyrie.actor.specialEffects.spark;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wilhelmsen.gamelib.utils.Vector2;

class SparkSegment {

    public final Vector2 from;
    public final Vector2 to;
    public final float rotation;
    private final float spriteHeight;
    private Color color;

    public SparkSegment(Vector2 from, Vector2 to, float rotation, Color color) {
        this.from = from;
        this.to = to;
        this.rotation = rotation;
        this.color = color;
        spriteHeight = new Vector2(to).sub(from).getMag();
    }

    public void draw(SpriteBatch sb) {
        Sprite sprite = SparkHandler.sprite;
        sprite.setX(from.x);
        sprite.setY(from.y + SparkHandler.texShift);
        sprite.setRotation(rotation);
        sprite.setColor(color);
        sprite.setSize(spriteHeight, SparkHandler.spriteWidth);

        sprite.draw(sb);
    }

    public void shapeDraw(ShapeRenderer sr) {
        sr.line(from.x, from.y, to.x, to.y);
    }
}