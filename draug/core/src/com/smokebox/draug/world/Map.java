package com.smokebox.draug.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.smokebox.draug.GameScreen;
import com.smokebox.lib.pcg.dungeon.RoomSpreadDungeon;
import com.smokebox.lib.pcg.dungeon.RoomsWithTree;
import com.smokebox.lib.pcg.levelGeneration.TunnelDigger;
import com.smokebox.lib.utils.geom.Line;
import com.smokebox.lib.utils.geom.UnifiablePolyedge;
import com.smokebox.lib.utils.pathfinding.AStar;
import com.smokebox.lib.utils.pathfinding.Connection;
import com.smokebox.lib.utils.pathfinding.Euclidian;
import com.smokebox.lib.utils.pathfinding.StarNode;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Harald Floor Wilhelmsen
 *
 */
public class Map {

	private AStar pathfinder;

	GameScreen screen;

	public World world;
	private MyContactListener contactListener;

	private class MyContactFilter implements ContactFilter {
		@Override
		public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
			return ContactGroups.matrix[fixtureA.getFilterData().groupIndex][fixtureB.getFilterData().groupIndex];
		}
	}

	public enum WorldTypes {
		CAVES(),
		DUNGEON();
	}

	public Map(WorldTypes worldType, int size, long seed, GameScreen screen) {
		this.screen = screen;

		pathfinder = new AStar();
		if (seed < 0) seed = System.nanoTime();

		int[][] asInt;

		switch(worldType) {
			case CAVES:
				asInt = TunnelDigger.tunnelDiggerWorld(500, 0);
				break;
			default: // case DUNGEON:
				RoomsWithTree dungeon = RoomSpreadDungeon.RoomSpreadFloor(size, 5, 5, new Random(seed));
				asInt = RoomSpreadDungeon.asInt2(dungeon);
				break;
		}

		UnifiablePolyedge p = new UnifiablePolyedge(asInt).unify();
		pathfinder.defineWorldFromPolyedge(p);

		world = new World(new Vector2(), false);
		world.setContactFilter(new MyContactFilter());
		world.setContactListener(contactListener = new MyContactListener(world));

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.fixedRotation = true;
		bodyDef.awake = true;
		Body body = world.createBody(bodyDef);

		for (Line l : p.getEdges()) {
			FixtureDef fix = new FixtureDef();
			EdgeShape e = new EdgeShape();
			e.set(l.x, l.y, l.x2, l.y2);
			fix.shape = e;
			fix.density = 1;
			fix.isSensor = false;
			Fixture fixture = body.createFixture(fix);
			fixture.setFilterData(ContactGroups.WORLD.filter);
		}
	}

	public Vector2 getSpawn() {
		Connection c = pathfinder.getNodes().get(0).getConnections().get(0);
		StarNode n1 = c.start;
		StarNode n2 = c.end;
		return new Vector2(n1.x + (n2.x - n1.x)/2, n1.y + (n2.y - n1.y)/2);
	}

	public void update(float delta) {
		world.step(delta, 6, 3);

		contactListener.update();

		if (Gdx.input.isKeyPressed(Input.Keys.UP)) screen.game.cam.position.add(0, 1, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) screen.game.cam.position.add(-1, 0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) screen.game.cam.position.add(1, 0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) screen.game.cam.position.add(0, -1, 0);
	}

	public void drawPathGraph(ShapeRenderer sr) {
		ArrayList<StarNode> stars = pathfinder.getNodes();
		for (StarNode s : stars) {
			sr.setColor(0, 0.4f, 0.4f, 1);
			for (Connection c : s.getConnections()) {
				sr.line(c.start.x, c.start.y, c.end.x, c.end.y);
				sr.circle(c.start.x + (c.end.x - c.start.x) * 0.5f, c.start.y + (c.end.y - c.start.y) * 0.5f, 0.1f, 8);
			}
			sr.setColor(0, 0.8f, 0.8f, 1);
			sr.circle(s.x, s.y, 0.2f, 4);
		}

	}

//	public void drawFancyWalls(ShapeRenderer sr, Vector2 cam, float cameraHeight) {
//		for(HalfSpace w : walls) {
//			Line l = w.line;
//			if(screen.isOnScreen(l.x, l.y) || screen.isOnScreen(l.x2, l.y2)) {
//				Vector2 wDist = new Vector2();
//				float angle;
//				float r;
//				float s = 1/cameraHeight;
//
//				wDist.set(l.x - cam.x, l.y - cam.y);
//				r = wDist.len();
//				angle = wDist.angleRad();
//				Vector2 wc1 = new Vector2(	l.x		+ s * r * (float)Math.cos(angle),
//											l.y 	+ s * r * (float)Math.sin(angle)
//											);
//
//				wDist.set(l.x2 - cam.x, l.y2 - cam.y);
//				r = wDist.len();
//				angle = wDist.angleRad();
//				Vector2 wc2 = new Vector2(	l.x2	+ s * r * (float)Math.cos(angle),
//											l.y2	+ s * r * (float)Math.sin(angle)
//											);
//
//				float colorIncScl = 1;
//				float[] c = MathUtils.HSLtoRGB(l.y * colorIncScl, 1, 1);
//				float[] c2 = MathUtils.HSLtoRGB(l.y2*colorIncScl, 1, 1);
//				sr.line(l.x, l.y, l.x2, l.y2, new Color(c[0], c[1], c[2], c[3]), new Color(c2[0], c2[1], c2[2], c2[3]));
//
//				c = MathUtils.HSLtoRGB(l.y*colorIncScl, 1, 1);
//				c2 = MathUtils.HSLtoRGB(wc1.y*colorIncScl, 1, 1);
//				sr.line(l.x, l.y, wc1.x, wc1.y, new Color(c[0], c[1], c[2], c[3]), new Color(c2[0], c2[1], c2[2], c2[3]));
//
//				c = MathUtils.HSLtoRGB(l.y2*colorIncScl, 1, 1);
//				c2 = MathUtils.HSLtoRGB(wc2.y*colorIncScl, 1, 1);
//				sr.line(l.x2, l.y2, wc2.x, wc2.y, new Color(c[0], c[1], c[2], c[3]), new Color(c2[0], c2[1], c2[2], c2[3]));
//
//				c = MathUtils.HSLtoRGB(wc1.y*colorIncScl, 1, 1);
//				c2 = MathUtils.HSLtoRGB(wc2.y*colorIncScl, 1, 1);
//				sr.line(wc1.x, wc1.y, wc2.x, wc2.y, new Color(c[0], c[1], c[2], c[3]), new Color(c2[0], c2[1], c2[2], c2[3]));
//			}
//		}
//	}

	/**
	 * Pathplanning
	 * Uses an implementation of the A*-algorithm to find a path on the
	 * previously generated graph from coordinates "start" to "end"
	 *
	 * @param start Coordinated to start from
	 * @param end   Coordinates to end on
	 * @return A list of points the character must go through
	 */
	public ArrayList<Vector2> getPath(Vector2 start, Vector2 end) {
		ArrayList<Vector2> path = new ArrayList<Vector2>();
		path.add(start);
		StarNode endNode = pathfinder.getNodeClosestTo(end.x, end.y);
		ArrayList<StarNode> nodePath = pathfinder.findPath(pathfinder.getNodeClosestTo(start.x, start.y), endNode, new Euclidian(end.x, end.y));
		if (nodePath != null) {
			for (StarNode i : nodePath) {
				path.add(new Vector2(i.x, i.y));
			}
		}
		return path;
	}
}