package com.smokebox.valkyrie.actor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;

public class HeadsUpDisplay {

	PlayableCharacter player;
	
	BitmapFont font;
	
	float healthWidth = 10;
	
	Sprite healthInside;
	Sprite healthStroke;
	
	
	
	public HeadsUpDisplay(PlayableCharacter player, AssetManager astMng) {
		this.player = player;
		
		
	}
	
	public void draw(SpriteBatch sb) {
		
	}
}
