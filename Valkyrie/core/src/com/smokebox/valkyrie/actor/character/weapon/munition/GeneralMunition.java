package com.smokebox.valkyrie.actor.character.weapon.munition;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smokebox.valkyrie.actor.ability.DamageReceiver;
import com.smokebox.valkyrie.actor.ability.Movable;

/**
 * Created by Harald Wilhelmsen on 10/3/2014.
 */
public abstract class GeneralMunition {

    public abstract void onHit(Movable mv, DamageReceiver dmgRec);

    public abstract boolean update(float delta);

    public abstract void draw(SpriteBatch sb);
}
