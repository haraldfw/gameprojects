package com.smokebox.valkyrie.module.skill;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader;

public interface SkillModule {

    public static void initializeConstants(XmlReader.Element xml) {
    }

    public boolean update(float delta);

    public void draw(SpriteBatch sb);
}
