package com.wilhelmsen.exile.screen;

import com.badlogic.gdx.Screen;
import com.wilhelmsen.exile.ExileGame;

/**
 * Created by Harald on 28.02.2017.
 */
public abstract class ExileScreen implements Screen {
    public final ExileGame game;

    public ExileScreen(ExileGame game) {
        this.game = game;
    }
}
