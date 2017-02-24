package com.smokebox.valkyrie.module.movement;

import com.smokebox.valkyrie.actor.ability.Movable;

public interface MovementExecutionModule {

	public void update(Movable c, float delta);
}
