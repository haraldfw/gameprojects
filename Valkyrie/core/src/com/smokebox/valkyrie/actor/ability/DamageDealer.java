package com.smokebox.valkyrie.actor.ability;

import com.smokebox.valkyrie.actor.character.GeneralCharacter.Team;

public interface DamageDealer {

	public void dealDamage(DamageReceiver rec, float multiplier);
	public Team getTeam();
}
