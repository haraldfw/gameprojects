package com.smokebox.valkyrie.trigger;

import com.smokebox.valkyrie.actor.ability.DamageDealer;

public abstract class DamageTrigger extends Trigger {

    public float dmgMul;
    public DamageDealer owner;
}
