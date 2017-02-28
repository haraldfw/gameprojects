package com.smokebox.valkyrie.actor.specialEffects.lightning;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.XmlReader;
import com.smokebox.valkyrie.Game;
import com.wilhelmsen.gamelib.utils.Vector2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Harald Wilhelmsen on 8/21/2014.
 */
class LightningBolt {
    private static float sway = 1;
    private static float jag = 1;
    private static float segmentsPerTile = 5;
    public ArrayList<LightningSegment> segments;
    float alpha;
    LightningHandler handler;

    public LightningBolt(Vector2 source, Vector2 dest,
                         float duration, LightningHandler handler) {
        alpha = duration;

        this.handler = handler;

        segments = new ArrayList<LightningSegment>();
        Vector2 tangent = new Vector2(dest).sub(new Vector2(source));
        Vector2 normal = tangent.getNormal().nor();
        float length = tangent.getMag();

        ArrayList<Float> positions = new ArrayList<Float>();
        positions.add(0f);
        for (int i = 0; i < Math.round(length * segmentsPerTile); i++)
            positions.add((float) Math.random());

        Collections.sort(positions);

        Vector2 prevPoint = source;
        float prevDisplacement = 0;
        for (int i = 1; i < positions.size(); i++) {
            float pos = positions.get(i);

            // used to prevent sharp angles by ensuring very close positions also have small perpendicular variation.
            float scale = (length * jag) * (pos - positions.get(i - 1));

            // defines an envelope. Points near the middle of the bolt can be further from the central line.
            float envelope = pos > 0.95f ? 20 * (1 - pos) : 1;

            float displacement = (float) ((Math.random() * 2 - 1) * sway);
            displacement -= (displacement - prevDisplacement) * (1 - scale);
            displacement *= envelope;

            Vector2 point = new Vector2(source).add(new Vector2(0, 0).addScaledVector(tangent, pos)).add(new Vector2().addScaledVector(normal, displacement));
            segments.add(new LightningSegment(prevPoint, point, handler));
            prevPoint = point;
            prevDisplacement = displacement;
        }

        segments.add(new LightningSegment(new Vector2(prevPoint), new Vector2(dest), handler));
    }

    public static void initializeConstants(XmlReader.Element xml) {
        XmlReader.Element owner = Game.getOwnerByName("lightning", xml);
        sway = Game.getStatByName("sway", owner);
        jag = Game.getStatByName("jag", owner);
        segmentsPerTile = Game.getStatByName("segmentsPerTile", owner);
    }

    public boolean update(float delta) {
        return (alpha -= delta) > 0;
    }

    public void draw(SpriteBatch sb) {
        for (LightningSegment l : segments) l.draw(sb);
    }

    public void shapeDraw(ShapeRenderer sr) {
        for (LightningSegment l : segments) l.shapeDraw(sr);
        ;
    }

    public void printStatus() {
        System.out.println("\tsegments: " + segments.size());
    }
}
