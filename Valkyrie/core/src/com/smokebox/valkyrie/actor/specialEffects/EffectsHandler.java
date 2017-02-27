package com.smokebox.valkyrie.actor.specialEffects;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.specialEffects.fire.FireEmitter;
import com.smokebox.valkyrie.actor.specialEffects.fire.FireHandler;
import com.smokebox.valkyrie.actor.specialEffects.lightning.LightningHandler;
import com.smokebox.valkyrie.actor.specialEffects.spark.SparkHandler;

public class EffectsHandler {

    private LightningHandler lightning;
	private FireHandler fire;
	private SparkHandler spark;
	
	Game game;
	
	public enum Size {
		SMALL(1),
		MEDIUM(2),
		LARGE(3);
		
		public final float scale;
		Size(float scale) {
			this.scale = scale;
		}
	}
	
	public EffectsHandler(Game game) {
		this.game = game;
		
		lightning = new LightningHandler(game);
		fire = new FireHandler(game);
		spark = new SparkHandler(game);
	}
	
	public static void initializeConstants(Element root) {
		FireHandler.initializeConstants(root);
        SparkHandler.initializeConstants(root);
        LightningHandler.initializeConstants(root);
	}
	
	public void update(float delta) {
		lightning.update(delta);
		fire.update(delta);
		spark.update(delta);
	}

	public void draw(SpriteBatch sb) {
		sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		lightning.draw(sb);
		spark.draw(sb);
		fire.draw(sb);
		sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void shapeDraw(ShapeRenderer sr) {
		lightning.shapeDraw(sr);
		spark.shapeDraw(sr);
		fire.shapeDraw(sr);
	}

	public void createBolt(Vector2 source, Vector2 dest, float duration) {
		lightning.createBolt(source, dest, duration);
	}
	
	public FireEmitter createFire(Vector2 pos, Size size, float radius, float intensity, float duration) {
		return fire.createEmitter(pos, size, radius, intensity, duration);
	}
	
	public void newSparkBurst(Vector2 pos, Vector2 vel, float radius, int amount, boolean fromCenter) {
		spark.newSparkBurst(pos, vel, radius, amount, fromCenter);
	}

	public void printStatus() {
		lightning.printStatus();
		fire.printStatus();
		spark.printStatus();
	}
}