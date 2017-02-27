package com.smokebox.valkyrie.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.wilhelmsen.gamelib.utils.Intersect;
import com.wilhelmsen.gamelib.utils.geom.Rectangle;
import com.smokebox.valkyrie.Interactable;
import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 9/22/2014.
 */
public class Room {

	TiledMap map;
	private MapLayer collLayer;
	private MapObjects objects;
	private int tileSize;
	private Door[] doors;
	private Floor floor;

	public Room(Floor floor, TiledMap map) {
		this.floor = floor;
		this.map = map;
		collLayer = map.getLayers().get("worldCollision");
		objects = map.getLayers().get("worldCollision").getObjects();
		tileSize = Integer.parseInt(map.getProperties().get("tileSize").toString());

		//transform worldCollision-rects
		Array<RectangleMapObject> rects = objects.getByType(RectangleMapObject.class);
		float scale = 2 / (float) tileSize;
		for (RectangleMapObject r : rects) {
			com.badlogic.gdx.math.Rectangle rect = r.getRectangle();
			rect.x *= scale;
			rect.y *= scale;
			rect.width *= scale;
			rect.height *= scale;
		}

		rects = map.getLayers().get("doorsIn").getObjects().getByType(RectangleMapObject.class);
		doors = new Door[rects.size];
		int i = 0; // This constructor must be placed in floor-generator.
		//for(RectangleMapObject r : rects) doors[i++] = new Door(r.getRectangle(), new Sprite(), new Room(map), false);


	}

	public void draw(TiledMapRenderer tmr) {
		tmr.render();
	}

	public ArrayList<Interactable> getInteractables(PlayableCharacter player) {
		ArrayList<Interactable> inters = new ArrayList<>();
		for (Door d : doors) {
			if (Intersect.intersection(d.rect, player.getBoundingBox())) inters.add(d);
		}
		return inters;
	}

	public void drawCollision(ShapeRenderer sr) {
		Array<RectangleMapObject> rs = getCollisionRects();

		for (RectangleMapObject r : rs) {
			com.badlogic.gdx.math.Rectangle rect = r.getRectangle();
			sr.rect(rect.x, rect.y, rect.width, rect.height);
		}
	}

	public Array<RectangleMapObject> getCollisionRects() {
		return objects.getByType(RectangleMapObject.class);
	}

	class Door implements Interactable {
		public Rectangle rect;
		public Door connection;
		public Room room;
		public boolean closed;
		private boolean locked; // needs key

		public Door(com.badlogic.gdx.math.Rectangle rect,
					Sprite sprite, Door connection, Room containing, boolean locked) {
			this.rect = new Rectangle(rect.x, rect.y, rect.width, rect.height);
			this.connection = connection;
			closed = true;
			this.locked = locked;
		}

		@Override
		public void interact(PlayableCharacter player) {
			floor.setRoom(connection.room);
			player.getPos().set(
									   connection.rect.x + connection.rect.width / 2 - player.getBoundingBox().width / 2,
									   connection.rect.y);
			floor.game.activateOnRoomEnter(connection.room);
		}

		@Override
		public String getOptionDialogue() {
			return locked ? "Use key to open door" : "Enter door";
		}
	}
}
