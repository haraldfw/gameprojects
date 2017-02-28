package com.smokebox.valkyrie.event;

import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;

public interface OnPlayerHit {

    public float onPlayerHit(PlayableCharacter player, float dmgMul);
}
