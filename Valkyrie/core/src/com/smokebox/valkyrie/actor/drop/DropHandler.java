package com.smokebox.valkyrie.actor.drop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.XmlReader;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.ability.Drawable;
import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;
import com.smokebox.valkyrie.module.movement.MovementExecutionModule;
import com.wilhelmsen.gamelib.utils.Vector2;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 9/29/2014.
 */
public class DropHandler {

    public Animation keyAnim;
    public Animation bombAnim;
    public Animation coinAnim;
    public MovementExecutionModule move;
    Game game;
    private ArrayList<GeneralDrop> drops;

    public DropHandler(MovementExecutionModule move, XmlReader.Element root, Game game) {
        this.move = move;
        drops = new ArrayList<GeneralDrop>();
        keyAnim = new Animation(0.1f, Drawable.extractFrames(game.getAssetManager().get("data/drop/key.png"), 5, 9));
        bombAnim = new Animation(0.1f, Drawable.extractFrames(game.getAssetManager().get("data/drop/bomb.png"), 9, 11));
        this.game = game;
    }

    public static void loadAssets(AssetManager astmng) {
        astmng.load("data/drop/key.png", Texture.class);
        astmng.load("data/drop/bomb.png", Texture.class);
    }

    public void update(PlayableCharacter player, float delta) {
        for (int i = 0; i < drops.size(); ) {
            GeneralDrop drop = drops.get(i);
            if (!drop.update(player, delta)) drops.remove(i);
            else i++;
        }
        for (GeneralDrop drop : drops) {

        }
    }

    public void draw(SpriteBatch sb) {
        for (GeneralDrop drop : drops) drop.draw(sb);
    }

    public void debugDraw(ShapeRenderer sr) {
        for (GeneralDrop d : drops) d.debugDraw(sr);
    }

    public void addKey(Vector2 pos, Vector2 vel) {
        drops.add(new KeyDrop(pos, vel, this));
    }

    public void addBomb(Vector2 pos, Vector2 vel) {
        drops.add(new BombDrop(pos, vel, this));
    }
/*
    public void addCoin(Vector2 pos, Vector2 vel) {
		drops.add(new CoinDrop(pos, vel, this));
	}
	*/
}
