package com.smokebox.valkyrie.actor.specialEffects.fire;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smokebox.valkyrie.actor.specialEffects.EffectsHandler;
import com.wilhelmsen.gamelib.utils.Vector2;

public class FireParticle {

    private final float maxLife;
    private final Vector2 pos;
    private final Vector2 vel;
    private final Sprite localSprite;
    float shift;
    private float life;
    private float rotation;

    public FireParticle(Vector2 pos, Vector2 vel, float life, EffectsHandler.Size size, FireHandler handle) {
        this.pos = new Vector2(pos);
        this.vel = new Vector2(vel);
        this.life = 0;
        maxLife = life;

        switch (size) {
            case LARGE:
                localSprite = handle.spriteLarge;
                break;
            case MEDIUM:
                localSprite = handle.spriteMedium;
                break;
            default: // case SMALL
                localSprite = handle.spriteSmall;
                break;
        }

        shift = -localSprite.getWidth() / 2f;

        rotation = (float) Math.random() * 360f;
    }

    /**
     * Updates this particle
     *
     * @param delta Time since last update
     * @return True if particles still active. False if not.
     */
    public boolean update(float delta) {
        if ((life += delta) > maxLife) return false;
        pos.addScaledVector(vel, delta);
        vel.x *= 0.9f;
        vel.y += FireHandler.particleGravitation * delta;
        rotation += 10 * delta * Math.signum(rotation);
        return true;
    }

    public void draw(SpriteBatch sb) {
        localSprite.setPosition(pos.x + shift, pos.y + shift);

        if (life > 0.2f)
            localSprite.setColor(1, 1 - (1 / maxLife) * life, 0, (maxLife - life) / maxLife);
        else
            localSprite.setColor(1, 0.8f - (1 / maxLife) * life, 0, 1);
        localSprite.setRotation(rotation);
        localSprite.draw(sb);
    }

    public void shapeDraw(ShapeRenderer sr) {
        sr.circle(pos.x, pos.y, localSprite.getRegionWidth() / 2, 4);
    }
}
