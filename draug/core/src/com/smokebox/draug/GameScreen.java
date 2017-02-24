package com.smokebox.draug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.smokebox.draug.world.ContactGroups;
import com.smokebox.draug.world.Map;

/**
 * Created by Harald Wilhelmsen on 10/10/2014.
 */
public class GameScreen extends ScreenAdapter {

	public DraugGame game;
	Map map;

	public Box2DDebugRenderer debugRenderer;
	ShapeRenderer sr;

	Player player;

	boolean wasReleased = false;

	public GameScreen(DraugGame game) {
		this.game = game;
		map = new Map(Map.WorldTypes.DUNGEON, 150, (long)(Math.random()*10000f), this);

		debugRenderer = new Box2DDebugRenderer(
													  true,	// bodies
													  false,	// joints
													  false,	// AABBs
													  true,		// inactives
													  false,	// velocities
													  false);	//	contacts
		player = new Player(map.getSpawn(), this);
		sr = new ShapeRenderer();
	}

	@Override
	public void render(float delta) {
		map.update(delta);
		player.update(delta);
		if( Gdx.input.isKeyPressed(Input.Keys.SPACE)) game.cam.position.set(player.body.getPosition(), 0);
		sr.setProjectionMatrix(game.cam.combined);
		sr.begin(ShapeRenderer.ShapeType.Line);
		map.drawPathGraph(sr);
		sr.end();
		debugRenderer.render(map.world, game.cam.combined);

		if(Gdx.input.isKeyPressed(Input.Keys.P))player.body.setTransform(map.getSpawn(), 0);

		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) new Bolt(game.mousePos(), game.mousePos().sub(player.body.getPosition()), this);

		if(Gdx.input.isKeyPressed(Input.Keys.O) && wasReleased) {
			wasReleased = false;
			BodyDef d = new BodyDef();
			Body b = map.world.createBody(d);
			b.setType(BodyDef.BodyType.DynamicBody);
			CircleShape c = new CircleShape();
			c.setRadius(1);
			Fixture f = b.createFixture(c, 0.1f);
			f.setFilterData(ContactGroups.ENEMY.filter);
			b.setTransform(game.mousePos(), 0);
		}

		if(!Gdx.input.isKeyPressed(Input.Keys.O)) wasReleased = true;
	}
}
