package com.smokebox.draug.testBed.WorldGenerator;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smokebox.draug.DraugGame;
import com.smokebox.lib.pcg.roomBased.RoomDef;

/**
 * Created by Harald Wilhelmsen on 10/10/2014.
 */
public class TestBedScreen extends ScreenAdapter {

	public DraugGame game;

	ShapeRenderer sr;
	RoomDef[][] world;

	public TestBedScreen(DraugGame game) {
		this.game = game;
		sr = new ShapeRenderer();
		//Generator.generate2dArray(15, new RoomType[]{}, 0.1f, 3, 3, null);
	}

	@Override
	public void render(float delta) {
		sr.begin(ShapeRenderer.ShapeType.Line);

		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				if(world[i][j] != null) sr.rect(i, j, 1, 1);
			}
		}

		sr.end();
	}
}
