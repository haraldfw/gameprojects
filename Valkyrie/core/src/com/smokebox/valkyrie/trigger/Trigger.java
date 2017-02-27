package com.smokebox.valkyrie.trigger;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wilhelmsen.gamelib.utils.geom.Circle;
import com.wilhelmsen.gamelib.utils.geom.Rectangle;

public abstract class Trigger  {

    /**
     * Returns wether the trigger is still active.
     * If sub-class does not override this method the
     * trigger is always active.
     * @param delta Time passed
     * @return  True if active
     */
    public boolean isActive(float delta) {
        return true;
    }

    /**
     * Collision-method against a circle
     * @param c Circle to check worldCollision with
     * @return  True if worldCollision
     */
	public abstract boolean circle(Circle c);

    /**
     * Collision-method against rectangle
     * @param r Rectangle to check worldCollision with
     * @return  True if worldCollision
     */
	public abstract boolean rect(Rectangle r);

	public abstract void debugDraw(ShapeRenderer sr);
}
