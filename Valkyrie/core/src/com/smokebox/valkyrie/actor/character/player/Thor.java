package com.smokebox.valkyrie.actor.character.player;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.ability.Drawable;
import com.smokebox.valkyrie.actor.character.weapon.Revolver;
import com.smokebox.valkyrie.module.activation.Activation_buttonA;
import com.smokebox.valkyrie.module.activation.Activation_buttonLB;
import com.smokebox.valkyrie.module.movement.MoveMotivationModule;
import com.smokebox.valkyrie.module.movement.MovementExecutionModule;
import com.smokebox.valkyrie.module.skill.Jump;
import com.smokebox.valkyrie.module.skill.LightningDash;
import com.smokebox.valkyrie.module.skill.SkillModule;
import com.wilhelmsen.gamelib.utils.Intersect;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.wilhelmsen.gamelib.utils.geom.Circle;
import com.wilhelmsen.gamelib.utils.geom.Line;
import com.wilhelmsen.gamelib.utils.geom.Rectangle;

import java.util.ArrayList;

public class Thor extends PlayableCharacter {

    boolean weaponAvailable = true;

    Rectangle currentHitbox = new Rectangle();


    public Thor(Vector2 pos,
                MoveMotivationModule mm,
                MovementExecutionModule me,
                Game game, Element root) {
        super(pos, mm, me, new Animation[0], game, game.getOwnerByName("thor", root));
        ArrayList<SkillModule> skills = new ArrayList<SkillModule>();
        skills.add(new LightningDash(game, new Activation_buttonLB(game.getInputEntity()), this, root));
        skills.add(new Jump(new Activation_buttonA(game.getInputEntity()), this));
        setSkills(skills);

        Animation walk = new Animation(0.05f, Drawable.extractFrames(game.getAssetManager().get("data/char/thor_walk.png"), 10, 12));
        Animation idle = new Animation(0.075f, Drawable.extractFrames(game.getAssetManager().get("data/char/thor_idle.png"), 10, 12));
        setAnimations(new Animation[]{idle, walk});

        weapon = new Revolver(this, root, game);
    }

    public static void loadAssets(AssetManager astmng) {
        astmng.load("data/char/thor_walk.png", Texture.class);
        astmng.load("data/char/thor_idle.png", Texture.class);
        Revolver.loadAssets(astmng);
    }

    @Override
    public void update(float delta) {
        updateCurrentHitbox();
        super.update(delta);
        if (game.getInputEntity().rt()) weapon.attack();
    }

    @Override
    public Vector2 getWeaponMuzzle() {
        return new Vector2(pos).add(0.25f, 0.25f);
    }

    @Override
    public boolean col_line(Line l) {
        return Intersect.intersection(l, currentHitbox);
    }

    @Override
    public boolean col_circle(Circle c) {
        return Intersect.rectCircle(c, currentHitbox);
    }

    @Override
    public boolean col_Rect(Rectangle r) {
        return Intersect.intersection(currentHitbox, r);
    }

    private void updateCurrentHitbox() {
        currentHitbox.x = pos.x + (super.lookingRight() ? 3f * game.pixelSize : 0);
        currentHitbox.y = pos.y;
        currentHitbox.width = 7 * game.pixelSize;
        currentHitbox.height = 12 * game.pixelSize;
    }

    @Override
    public Rectangle getBoundingBox() {
        return currentHitbox;
    }

    @Override
    public boolean weaponIsAvailable() {
        return weaponAvailable;
    }

    @Override
    public Vector2 getAttackRootCoordinates() {
        return new Vector2(pos).add(currentHitbox.width / 2, 0);
    }
}
