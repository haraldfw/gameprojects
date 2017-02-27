package com.smokebox.valkyrie.event;

import com.smokebox.valkyrie.actor.ability.DamageReceiver;

public interface OnSuccessfulAttack {

    public void onSuccessfulAttack(DamageReceiver rec, float dmgMul);
}
