/**
 *
 */
package com.smokebox.kraken.ability;

import com.wilhelmsen.gamelib.utils.Vector2;

/**
 * @author Harald Floor Wilhelmsen
 */
public interface CollidesWithWorld {

    public Vector2 getPos();

    public Vector2 getVel();

    public float getBoundingRadius();

    public void handleWallIntersection(Vector2 penetration);

    public void handleOutsideWalls(Vector2 moveBy);

    public boolean isAlive();
}
