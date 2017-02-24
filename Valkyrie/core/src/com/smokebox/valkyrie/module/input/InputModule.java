package com.smokebox.valkyrie.module.input;

import com.badlogic.gdx.controllers.PovDirection;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.valkyrie.module.input.XboxController.XboxButton;

public interface InputModule {

	public boolean button(XboxButton button);
	
	public boolean a();
	public boolean b();
	public boolean x();
	public boolean y();

	public boolean lb();
	public boolean rb();
	public boolean l3();
	public boolean r3();
	
	public boolean lt();
	public boolean rt();
	
	public boolean start();
	public boolean back();
	
	
	public float leftStickHor();
	public float leftStickVer();

	public float rightStickHor();
	public float rightStickVer();
	
	public PovDirection dPad();

    public Vector2 aimDirection(Vector2 fromPos);
}
