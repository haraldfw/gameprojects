package com.smokebox.valkyrie.module.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.PovDirection;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.module.input.XboxController.XboxButton;
import com.wilhelmsen.gamelib.utils.Vector2;

public class KeyboardAndMouse implements InputModule {

    @Override
    public Vector2 aimDirection(Vector2 fromPos) {
        return new Vector2(
                Gdx.input.getX() / (float) Game.pixelsPerTileSide,
                (Gdx.graphics.getHeight() - Gdx.input.getY()) / Game.pixelsPerTileSide).sub(fromPos);
    }

    @Override
    public boolean a() {
        return Gdx.input.isKeyPressed(Keys.SPACE);
    }

    @Override
    public boolean b() {
        return Gdx.input.isKeyPressed(Keys.B);
    }

    @Override
    public boolean x() {
        return Gdx.input.isKeyPressed(Keys.X);
    }

    @Override
    public boolean y() {
        return Gdx.input.isKeyPressed(Keys.Y);
    }

    @Override
    public boolean lb() {
        return Gdx.input.isKeyPressed(Keys.J);
    }

    @Override
    public boolean rb() {
        return Gdx.input.isKeyPressed(Keys.K);
    }

    @Override
    public boolean l3() {
        return Gdx.input.isKeyPressed(Keys.CONTROL_LEFT);
    }

    @Override
    public boolean r3() {
        return false;
    }

    @Override
    public boolean start() {
        return Gdx.input.isKeyPressed(Keys.ESCAPE);
    }

    @Override
    public boolean back() {
        return Gdx.input.isKeyPressed(Keys.BACKSPACE);
    }

    @Override
    public float leftStickHor() {
        boolean l = Gdx.input.isKeyPressed(Keys.A);
        boolean r = Gdx.input.isKeyPressed(Keys.D);

        float f = 0;

        if (l && !r) f = -1;
        else if (r && !l) f = 1;
        return f;
    }

    @Override
    public float leftStickVer() {
        boolean d = Gdx.input.isKeyPressed(Keys.S);
        boolean u = Gdx.input.isKeyPressed(Keys.W);

        float f = 0;

        if (d && !u) f = -1;
        else if (u && !d) f = 1;

        return f;
    }

    @Override
    public float rightStickHor() {
        return 0;
    }

    @Override
    public float rightStickVer() {
        return 0;
    }

    @Override
    public PovDirection dPad() {
        return null;
    }

    @Override
    public boolean button(XboxButton button) {
        return rt();
    }

    @Override
    public boolean lt() {
        return Gdx.input.isKeyPressed(Keys.H);
    }

    @Override
    public boolean rt() {
        return Gdx.input.isKeyPressed(Keys.L);
    }

}
