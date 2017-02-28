package com.smokebox.valkyrie.module.skill;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.character.GeneralCharacter;
import com.smokebox.valkyrie.module.activation.ActivationModule;
import com.wilhelmsen.gamelib.utils.Vector2;

public class Jump implements SkillModule {

    private static float jumpScale;
    private ActivationModule activation;
    private GeneralCharacter character;

    public Jump(ActivationModule a, GeneralCharacter character) {
        activation = a;
        this.character = character;
    }

    public static void initializeConstants(Element root) {
        jumpScale = Game.getStatByName("jumpScale", Game.getOwnerByName("jump", root));
    }

    @Override
    public boolean update(float delta) {
        if (character.grounded() && activation.activate()) {
            character.addImpulse(new Vector2(0, character.getJumpStrength()));
            return true;
        }
        return false;
    }

    @Override
    public void draw(SpriteBatch sb) {

    }
}