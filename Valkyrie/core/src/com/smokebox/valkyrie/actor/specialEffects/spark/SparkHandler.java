package com.smokebox.valkyrie.actor.specialEffects.spark;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.specialEffects.SmallEffect;

import java.util.ArrayList;

public class SparkHandler {
	
	Game game;
	
	static Sprite sprite;
	
	private ArrayList<SmallEffect> effects;
	
	static float particleGravitation = 0f;
	static float particleTimeAlive = 0.1f;
	static int particleTrail = 1;
	static float particleDamping = 1f;
	static float particleInitVel = 25f;
	
	static float texShift = 0;
	static float spriteWidth = 7f;
	
	public SparkHandler(Game game) {
		this.game = game;
		texShift = -3.5f*Game.pixelSize;
		spriteWidth = 7f*Game.pixelSize;
		
		sprite = new Sprite(new TextureRegion(game.getAssetManager().get("data/eff/sparkMid.png", Texture.class), 1, 7));
		sprite.setOrigin(0, 3.5f*Game.pixelSize);

		effects = new ArrayList<SmallEffect>();
	}
	
	public static void initializeConstants(Element root) {
		Element owner = Game.getOwnerByName("spark", root);
		particleGravitation = Game.getStatByName("particleGravitation", owner);
		particleTimeAlive = Game.getStatByName("particleTimeAlive", owner);
		particleTrail = (int)Game.getStatByName("particleTrail", owner);
		particleDamping = Game.getStatByName("particleDamping", owner);
		particleInitVel = Game.getStatByName("particleInitVel", owner);
	}

	public void update(float delta) {
		for(int i = 0; i < effects.size();) {
			SmallEffect e = effects.get(i);
			if(!e.update(delta)) effects.remove(i);
			else i++;
		}
	}

	public void draw(SpriteBatch sb) {
		for(SmallEffect e : effects) e.draw(sb);
	}

	public void shapeDraw(ShapeRenderer sr) {
		for(SmallEffect e : effects) e.shapeDraw(sr);
	}
	
	public void newSparkBurst(Vector2 pos, Vector2 vel, float radius, int amount, boolean fromCenter) {
		effects.add(new SparkBurst(pos, vel, amount, radius, fromCenter));
	}
	
	private class SparkBurst implements SmallEffect {
		
		private ArrayList<Spark> sparks;
		
		public SparkBurst(Vector2 pos, Vector2 vel, int amount, float radius, boolean fromCenter) {
			sparks = new ArrayList<Spark>();
			for(int i = 0; i < amount; i++) {
				sparks.add(
						new Spark(
								new Vector2(pos).add(Vector2.getRandomDirection().scl(radius)), 
								new Vector2(Math.random()*2*Math.PI).nor().scl(Math.random()*particleInitVel).add(vel), 
								new Color(0, 0.75f, 1, 1)));
			}
		}
		
		public boolean update(float delta) {
			for(int i = 0; i < sparks.size();) {
				Spark s = sparks.get(i);
				if(!s.update(delta)) sparks.remove(i);
				else i++;
			}
			return !sparks.isEmpty();
		}
		
		public void draw(SpriteBatch sb) {
			for(Spark s : sparks) s.draw(sb);
		}
		
		public void shapeDraw(ShapeRenderer sr) {
			for(Spark s : sparks) s.shapeDraw(sr);
		}
	}

	public static void loadAssets(AssetManager astmng) {
		astmng.load("data/eff/sparkMid.png", Texture.class);
	}

	public void printStatus() {
		System.out.println("Sparks active: \t" + effects.size());
	}
}
