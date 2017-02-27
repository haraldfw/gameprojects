package com.smokebox.valkyrie.actor.specialEffects.fire;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.specialEffects.EffectsHandler.Size;

import java.util.ArrayList;
import java.util.Random;

public class FireHandler {
	
	Random random;

	private ArrayList<FireEmitter> emitters;
	
	static float particleInitVelScale = 1f;
	static float particleMaxLife = 1f;
    static float particleGravitation = 5f;
	static float largesAmount = 1f/12f;
	static float mediumsAmount = 1f/6f;
	
	Game game;
	
	public Sprite spriteSmall;
	public Sprite spriteMedium;
	public Sprite spriteLarge;
	
	public FireHandler(Game game) {
		random = new Random();

        emitters = new ArrayList<FireEmitter>();

		spriteSmall = new Sprite(game.getAssetManager().get("data/eff/fireSmall.png", Texture.class));
		spriteSmall.setSize(game.pixelSize*spriteSmall.getRegionWidth(), game.pixelSize*spriteSmall.getRegionHeight());
		spriteSmall.setOriginCenter();
		
		spriteMedium = new Sprite(game.getAssetManager().get("data/eff/fireMedium.png", Texture.class));
		spriteMedium.setSize(game.pixelSize*spriteMedium.getRegionWidth(), game.pixelSize*spriteMedium.getRegionHeight());
		spriteMedium.setOriginCenter();
		
		spriteLarge = new Sprite(game.getAssetManager().get("data/eff/fireLarge.png", Texture.class));
		spriteLarge.setSize(game.pixelSize*spriteLarge.getRegionWidth(), game.pixelSize*spriteLarge.getRegionHeight());
		spriteLarge.setOriginCenter();
		
		this.game = game;
	}
	
	public static void initializeConstants(Element root) {
		Element owner = Game.getOwnerByName("fire", root);
		particleInitVelScale = Game.getStatByName("particleInitVelScale", owner);
        particleGravitation = Game.getStatByName("particleGravitation", owner);
		particleMaxLife = Game.getStatByName("particleMaxLife", owner);
		largesAmount = Game.getStatByName("largesAmount", owner);
		mediumsAmount = Game.getStatByName("mediumsAmount", owner);
	}

	public void update(float delta) {
		for(int i = 0; i < emitters.size();) {
			FireEmitter e = emitters.get(i);
			// Updates all emitters
			if(!e.update(delta)) 
				// remove if emitter is done
				emitters.remove(i);
			else
				// continues if particle still alive
				i++;
		}
	}
	
	public void draw(SpriteBatch sb) {
		for(FireEmitter e : emitters) e.draw(sb);
	}

	public void shapeDraw(ShapeRenderer sr) {
		// lelelelelel der sheps
	}
	
	public FireEmitter createEmitter(Vector2 pos, Size size, float radius, float intensity, float duration) {
		FireEmitter e = new FireEmitter(pos, size, radius, intensity, duration, this);
		emitters.add(e);
		return e;
	}

	public static void loadAssets(AssetManager astmng) {
		astmng.load("data/eff/fireSmall.png", Texture.class);
		astmng.load("data/eff/fireMedium.png", Texture.class);
		astmng.load("data/eff/fireLarge.png", Texture.class);
	}

	public void printStatus() {
		System.out.println("Emitters active: \t " + emitters.size());
		for(FireEmitter e : emitters) e.printStatus();
	}
}
