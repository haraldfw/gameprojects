package com.smokebox.valkyrie.actor.ability;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Drawable {

    public static TextureRegion[] extractFrames(Texture t, int frameWidth, int frameHeight) {
        TextureRegion tmp[][] = TextureRegion.split(t, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[0].length; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return frames;
    }

    public void draw(SpriteBatch sb);

    public void debugDraw(ShapeRenderer sr);

    public void setAnimTimer(float f);
}
