package com.smokebox.draug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.smokebox.lib.utils.MathUtils;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 10/20/2014.
 */
public class LoadingScreen extends ScreenAdapter {

	final ShapeRenderer sr;

	final Vector2 c = new Vector2();
	final Vector2 zed = new Vector2();
	ArrayList<Ball> balls = new ArrayList<Ball>();

	float timePassed = 0;

	final DraugGame game;

	Vector2 pos = new Vector2();
	Vector2 vel = new Vector2();
	Vector2 acc = new Vector2();
	Vector2 fac = new Vector2();
	Vector2 dir = new Vector2(1, 0);

	public LoadingScreen(DraugGame game) {
		this.game = game;

		sr = new ShapeRenderer();
		sr.setProjectionMatrix(game.cam.combined);
	}

	@Override
	public void render(float delta) {
		timePassed += delta;

		sr.begin(ShapeRenderer.ShapeType.Filled);

		float s = 25;

		float w = game.cam.viewportWidth;
		float h = game.cam.viewportHeight;
		if(pos.x < 0) pos.x = w;
		if(pos.x > w) pos.x = 0;
		if(pos.y > h) pos.y = 0;
		if(pos.y < 0) pos.y = h;

		float vx;
		float vy;
		float sin = (float)Math.sin(timePassed*s);
		vx = -dir.y*sin*2;
		vy = dir.x*sin*2;

		pos.mulAdd(vel, delta);
		vel.mulAdd(acc, delta);
		acc.set(fac);
		fac.setZero();
		vel.scl(0.94f);
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) fac.mulAdd(dir, 50);
		if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT)) fac.mulAdd(dir, - 50);
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) dir.rotate(5f);
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dir.rotate(-5f);

		float r = 0.2f;
		float[] newColor = MathUtils.HSLtoRGB((float)(Math.sin(timePassed) + 1)*180, 1, 1);

		float time = 0.7f;

//		balls.add(new Ball(pos.x, pos.y, vx, vy, r, newColor, time));
//		balls.add(new Ball(pos.x, pos.y, -vx, -vy, r, newColor, time));

		balls.add(new Ball(pos.x, pos.y, vx*1.5f, vy*1.5f, r, newColor, time));
		balls.add(new Ball(pos.x, pos.y, -vx*1.5f, -vy*1.5f, r, newColor, time));

		balls.add(new Ball(pos.x, pos.y, -vel.x/3, -vel.y/3, r, newColor, time));

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		for(int i = 1; i < balls.size();) {
			Ball b = balls.get(i);
			if(b.update(delta)) {
				i++;
				b.draw(sr);
			} else {
				if(b.childTime > 0.1f) {
					Vector2 rand = new Vector2((float) Math.random() * 2 - 1, (float) Math.random() * 2 - 1).nor().scl(5);
					balls.add(new Ball(b.c.x, b.c.y, rand.x, rand.y, r, newColor, b.childTime));
					rand.rotate90(1);
					balls.add(new Ball(b.c.x, b.c.y, rand.x, rand.y, r, newColor, b.childTime));
					rand.rotate90(1);
					balls.add(new Ball(b.c.x, b.c.y, rand.x, rand.y, r, newColor, b.childTime));
					rand.rotate90(1);
					balls.add(new Ball(b.c.x, b.c.y, rand.x, rand.y, r, newColor, b.childTime));
				}
				balls.remove(i);
			}
		}
		Gdx.gl.glDisable(GL20.GL_BLEND);

		sr.end();
	}

	private class Ball {

		public final Circle c;

		private float vx;
		private float vy;

		private float[] color;

		private float timeAlive;
		private float childTime;

		public Ball(float x, float y, float vx, float vy, float r, float[] color, float aliveFor) {
			c = new Circle(x, y, r);
			this.vx = vx;
			this.vy = vy;
			this.color = color;
			timeAlive = aliveFor;
			childTime = aliveFor*0.5f;
		}

		public boolean update(float delta) {
			c.x += delta*vx;
			c.y += delta*vy;
			vx*= 0.99f;
			vy*= 0.99f;
			return (timeAlive -= delta) > 0;
		}

		public void draw(ShapeRenderer sr) {
			sr.setColor(color[0], color[1], color[2], 1);
			float d = c.radius;
			sr.circle(c.x, c.y, d - d/(timeAlive*timeAlive + 1), 10 + (int)Math.ceil(timeAlive));
		}

		@Override
		public String toString() {
			return "x: " + c.x + "  y: " + c.y + "  vx: " + vx + "  vy: " + vy + "  r: " + c.radius;
		}
	}
}
