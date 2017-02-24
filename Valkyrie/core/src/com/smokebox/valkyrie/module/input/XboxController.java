package com.smokebox.valkyrie.module.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.smokebox.lib.utils.Vector2;

public class XboxController implements InputModule {
	
	Controller c;
	float stickThreshold = 0.2f;

    public enum XboxButton {
		a, b, x, y,
		lb, rb,
		back, start,
		l3, r3,
		lt, rt
	}

	public XboxController(Controller c) {
		this.c = c;
	}

    @Override
    public Vector2 aimDirection(Vector2 fromPos) {
		Vector2 aim = new Vector2(rightStickHor(), -rightStickVer());
		if(Math.abs(aim.x) < stickThreshold) aim.x = 0;
		if(Math.abs(aim.y) < stickThreshold) aim.y = 0;
    	return aim;
    }

	@Override
	public boolean a() {
		return c.getButton(0);
	}

	@Override
	public boolean b() {
		return c.getButton(1);
	}

	@Override
	public boolean x() {
		return c.getButton(2);
	}

	@Override
	public boolean y() {
		return c.getButton(3);
	}

	@Override
	public boolean lb() {
		return c.getButton(4);
	}

	@Override
	public boolean rb() {
		return c.getButton(5);
	}

	@Override
	public boolean l3() {
		return c.getButton(8);
	}

	@Override
	public boolean r3() {
		return c.getButton(9);
	}

	@Override
	public boolean start() {
		return c.getButton(7);
	}

	@Override
	public boolean back() {
		return c.getButton(6);
	}

	@Override
	public float leftStickHor() {
		return c.getAxis(1);
	}

	@Override
	public float leftStickVer() {
		return c.getAxis(0);
	}

	@Override
	public float rightStickHor() {
		return c.getAxis(3);
	}

	@Override
	public float rightStickVer() {
		return c.getAxis(2);
	}

	@Override
	public PovDirection dPad() {
		return c.getPov(0);
	}

	@Override
	public boolean button(XboxButton button) {
		int ord = button.ordinal();
		if(ord < 10) return c.getButton(button.ordinal());
		else if(button == XboxButton.lt) return lt();
		else return rt();
	}

	@Override
	public boolean lt() {
		return c.getAxis(4) > stickThreshold;
	}

	@Override
	public boolean rt() {
		return c.getAxis(4) < -stickThreshold;
	}

}
