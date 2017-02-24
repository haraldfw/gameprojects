package com.smokebox.draug.miniGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Harald Wilhelmsen on 10/27/2014.
 */
public interface MovableEntity {

	public void update(float delta);
	public void draw(SpriteBatch sb);
	public Body getBody();

	/**
	 * @return True if alive. False if is to be removed.
	 */
	public boolean getStatus();
}
