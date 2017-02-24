package com.smokebox.valkyrie;

import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;

/**
 * Created by Harald Wilhelmsen on 9/20/2014.
 */
public interface Interactable {
	public void interact(PlayableCharacter player);
	public String getOptionDialogue();
}
