/**
 *
 */
package com.smokebox.kraken.ability;

import com.wilhelmsen.gamelib.utils.Vector2;

/**
 * @author Harald Floor Wilhelmsen
 */
public interface ImpulseAffected {

    public void addForce(Vector2 force);

    public void damage(float damage);

    public Vector2 getPos();

}
