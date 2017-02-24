package com.polarbirds.wendigo.world.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polarbirds.wendigo.graphics.model.Drawable;
import com.polarbirds.wendigo.phys.model.Collidable;

/**
 * Created by Harald on 30.08.2016.
 */
public abstract class Tile implements Drawable, Collidable {

    private float posx;
    private float posy;

    public Tile(float posx, float posy) {
        this.posx = posx;
        this.posy = posy;
    }

    @Override
    public final void draw(SpriteBatch batch) {
        batch.draw(getTexture(), posx, posy);
    }

    protected abstract Texture getTexture();
}
