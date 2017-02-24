package com.smokebox.valkyrie.module.movement;

import com.smokebox.valkyrie.module.input.InputModule;

public class MM_Input implements MoveMotivationModule {

	InputModule input;
	
	public MM_Input(InputModule input) {
		this.input = input;
	}

	@Override
	public float getMoveDirection() {
		float f = input.leftStickHor(); 
		return Math.abs(f) > 0.2f ? f : 0;
	}
}
