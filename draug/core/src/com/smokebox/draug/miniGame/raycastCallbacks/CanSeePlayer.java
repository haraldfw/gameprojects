package com.smokebox.draug.miniGame.raycastCallbacks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.smokebox.draug.miniGame.EnemyShip;
import com.smokebox.draug.miniGame.MiniGameScreen;

/**
 * Created by Harald Wilhelmsen on 10/28/2014.
 */
public class CanSeePlayer implements RayCastCallback {

	MiniGameScreen game;
	EnemyShip ship;

	public CanSeePlayer(EnemyShip ship, MiniGameScreen game) {
		this.ship = ship;
		this.game = game;
	}

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
		if(fixture.getBody().getUserData() == game.player.getBody().getUserData()) {
			ship.setCanSeePlayer(true);
			return 0;
		}
		return -1;
	}
}
