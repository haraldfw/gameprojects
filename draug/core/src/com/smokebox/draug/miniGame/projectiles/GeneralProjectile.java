package com.smokebox.draug.miniGame.projectiles;

import com.badlogic.gdx.physics.box2d.Body;
import com.smokebox.draug.miniGame.Ship;

/**
 * Created by Harald Wilhelmsen on 10/28/2014.
 */
public interface GeneralProjectile {

	public void hit(Ship hit);
	public boolean getActive();
	public void setActive(boolean flag);
	public Body getBody();
}
