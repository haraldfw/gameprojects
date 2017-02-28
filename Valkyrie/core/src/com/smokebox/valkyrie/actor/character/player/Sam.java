package com.smokebox.valkyrie.actor.character.player;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.XmlReader;
import com.smokebox.valkyrie.Game;
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

/**
 * Created by Harald Wilhelmsen on 9/2/2014.
 */
public class Sam extends PlayableCharacter {
    boolean weaponAvailable = true;

    Rectangle currentHitbox = new Rectangle();

    public Sam(Vector2 pos,
               MoveMotivationModule mm,
               MovementExecutionModule me,
               Game game, XmlReader.Element xml) {
        super(pos, mm, me, new Animation[0], game, xml);
        ArrayList<SkillModule> skills = new ArrayList<SkillModule>();
        skills.add(new LightningDash(game, new Activation_buttonLB(game.getInputEntity()), this, xml));
        skills.add(new Jump(new Activation_buttonA(game.getInputEntity()), this));
        setSkills(skills);

        Animation walk = new Animation(0.05f, extractFrames("D:/GoogleDrive/Dev/Valkyrie/Characters/thor_walk.png", 10, 12));
        Animation idle = new Animation(0.075f, extractFrames("D:/GoogleDrive/Dev/Valkyrie/Characters/thor_idle.png", 10, 12));
        setAnimations(new Animation[]{idle, walk});
    }

    private TextureRegion[] extractFrames(String filepath, int frameWidth, int frameHeight) {
        TextureRegion tmp[][] = TextureRegion.split(new Texture(new FileHandle(filepath)), frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[0].length; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return frames;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        updateCurrentHitbox();
    }

    @Override
    public Vector2 getWeaponMuzzle() {
        return new Vector2(pos).add(0.25f, 0.5f);
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
