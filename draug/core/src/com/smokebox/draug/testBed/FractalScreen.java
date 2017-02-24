package com.smokebox.draug.testBed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.smokebox.draug.DraugGame;

/**
 * Created by Harald Wilhelmsen on 11/15/2014.
 */
public class FractalScreen extends ScreenAdapter {

	DraugGame game;

	ShapeRenderer sr;
	float width;
	float height;
/*
	int res = 149;
	float increment = 37;
	int incrementsPerUpdate = 1;
	float velScale = 1;
	float drawScale = 1f;
*/
	int res = 512;
	float increment = 3;
	int incrementsPerUpdate = 9;
	float velScale = 1f;
	float drawScale = 1f;

	float marginX = 1f/2f;
	float marginY = 1f/2f;

	float currentPoint = 0;

	Vector2[] starts = new Vector2[res];
	Vector2[] points = new Vector2[res];
	Vector2[] velocities = new Vector2[res];


	public FractalScreen(DraugGame game) {
		this.game = game;
		sr = new ShapeRenderer();
		Gdx.gl20.glEnable(GL20.GL_ALIASED_LINE_WIDTH_RANGE);
		this.width = game.cam.viewportWidth;
		this.height = game.cam.viewportHeight;

		marginX *= width;
		marginY *= height;

		initCircleWave();
	}

	private void initCircleWave() {
		float radius = 1;
		for(int i = 0; i < res; i++) {
			points[i] = new Vector2((float) Math.cos(((float)i/res)*(Math.PI*2))*radius,
									(float) Math.sin(((float)i/res)*(Math.PI*2))*radius);
			starts[i] = new Vector2(points[i]);
			velocities[i] = new Vector2();
		}
	}

	@Override
	public void render(float delta) {
		sr.setProjectionMatrix(game.cam.combined);
		circleWave(delta);
	}

	boolean up = true;
	float time = 0;

	private void circleWave(float delta) {
		delta = 1f/60f;
		time += delta;

		float margin = 8;

		sr.begin(ShapeRenderer.ShapeType.Line);

		float sin = (float)(Math.sin(time) + 1)/2;
		sr.setColor(sin, 1 - sin, (float) (Math.cos(time) + 1) / 2f, 1);

		float accScale = 0.01f;
		for(int i = 0; i < res; i++) {
			Vector2 goal = starts[i];
			Vector2 point = points[i];
			Vector2 vel = velocities[i];

			Vector2 dir = new Vector2(point).sub(goal);
			dir.x = -dir.x*accScale;
			dir.y = -dir.y*accScale;

			vel.add(dir.x*velScale, dir.y*velScale);
//			vel.scl(0.99f);

			if(vel.len2() < 0.0000000001f) vel.setZero();
			point.add(vel);

			// render

			Vector2 nextPoint = points[i + 1 < res ? i + 1 : 0];
			sr.line(point.x*drawScale + marginX, point.y*drawScale + marginY,
					nextPoint.x*drawScale + marginX, nextPoint.y*drawScale + marginY);
		}
		sr.end();

		for(int i = 0; i < incrementsPerUpdate; i++) {
			int intCurr = (int) currentPoint;

			velocities[intCurr].add(new Vector2(starts[intCurr]).scl((up ? 1 : -1)*0.5f));
			int before = intCurr;
			currentPoint += increment;
			if(before != (int) currentPoint) up = !up;
			while(currentPoint >= res) currentPoint -= res;
		}
	}

	private void colorPlay(float delta) {
		delta = Gdx.graphics.getDeltaTime();
		time += delta/1000000f;
		sr.begin(ShapeRenderer.ShapeType.Point);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				float r = ((float) Math.sin(time*x*y) + 1)/2;
				float g = ((float) Math.cos(time*y*y) + 1)/2;
				float b = ((float) Math.sin(time*x*x));

				sr.setColor(r, g, b, 1);
				sr.point(x, y, 0);
			}
		}
		sr.end();
		Gdx.graphics.requestRendering();
	}
}
