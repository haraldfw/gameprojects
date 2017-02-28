package com.wilhelmsen.exile.screen;

import com.badlogic.gdx.Screen;
import com.wilhelmsen.exile.ExileGame;

/**
 * Created by Harald on 28.02.2017.
 */
abstract class ExileScreen implements Screen {
    ExileGame game;

    ExileScreen(ExileGame game) {
        this.game = game;
    }
}
