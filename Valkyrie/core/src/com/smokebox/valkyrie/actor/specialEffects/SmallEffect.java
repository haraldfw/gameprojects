package com.smokebox.valkyrie.actor.specialEffects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface SmallEffect {

    public boolean update(float delta);

    public void draw(SpriteBatch sb);

    public void shapeDraw(ShapeRenderer sr);
}
