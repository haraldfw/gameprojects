package com.smokebox.valkyrie.module.skill;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.character.GeneralCharacter;
import com.smokebox.valkyrie.module.activation.ActivationModule;

public class Jump implements SkillModule {

	private ActivationModule activation;
	private GeneralCharacter character;
	
	private static float jumpScale;
	
	public Jump(ActivationModule a, GeneralCharacter character) {
		activation = a;
		this.character = character;
	}

	@Override
	public boolean update(float delta) {
		if(character.grounded() && activation.activate()) {
			character.addImpulse(new Vector2(0, character.getJumpStrength()));
			return true;
		}
		return false;
	}

	@Override
	public void draw(SpriteBatch sb) {

	}

	public static void initializeConstants(Element root) {
		jumpScale = Game.getStatByName("jumpScale", Game.getOwnerByName("jump", root));
	}
}