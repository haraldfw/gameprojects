package com.smokebox.valkyrie.actor.specialEffects.lightning;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.valkyrie.Game;

import java.util.ArrayList;

public class LightningHandler {
	
	Sprite sMid;

	Game game;
	
	static float texShift;
	
	private ArrayList<LightningBolt> bolts;
	
	public LightningHandler(Game game) {
        Texture tex = game.getAssetManager().get("data/eff/lightning_mid.png", Texture.class);
		sMid = new Sprite(new TextureRegion(tex, 3, 0, 1, 7));
		sMid.setOrigin(0, 3.5f*Game.pixelSize);

        bolts =new ArrayList<LightningBolt>();
		this.game = game;
		
		texShift = -3.5f*Game.pixelSize;
	}
	
	public static void initializeConstants(Element xml) {
		LightningBolt.initializeConstants(xml);
	}
	
	public void update(float delta) {
		for(int i = 0; i < bolts.size(); i++) {
			LightningBolt l = bolts.get(i);
			if(l.update(delta)) {
				i++;
				continue;
			}
			bolts.remove(i);
		}
	}
	
	public void createBolt(Vector2 source, Vector2 dest, float duration) {
		bolts.add(new LightningBolt(new Vector2(source), new Vector2(dest), duration, this));
	}
	
	public void draw(SpriteBatch sb) {
		for(LightningBolt l : bolts) l.draw(sb);
	}
	
	public void shapeDraw(ShapeRenderer sr) {
		sr.setColor(1, 0, 0, 1);
		for(LightningBolt l : bolts) l.shapeDraw(sr);
	}

	public static void loadAssets(AssetManager astmng) {
		astmng.load("data/eff/lightning_mid.png", Texture.class);
	}

	public void printStatus() {
		System.out.println("Bolts active: " + bolts.size());
		for(LightningBolt b : bolts) b.printStatus();
	}
}
