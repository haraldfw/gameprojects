package com.polarbirds.wendigo.game;

import com.badlogic.gdx.Game;

/**
 * Created by Harald on 31.08.2016.
 */
public class WendigoGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }
}
