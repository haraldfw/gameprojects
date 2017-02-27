package com.smokebox.valkyrie.actor.ability;

import com.wilhelmsen.gamelib.utils.Vector2;
import com.wilhelmsen.gamelib.utils.geom.Rectangle;

public interface CollidesWorld {

    public Vector2 getPos();

    public Vector2 getVel();

    public Vector2 getAcc();

    public Rectangle getBoundingBox();

    public void setGrounded(boolean b);
}
