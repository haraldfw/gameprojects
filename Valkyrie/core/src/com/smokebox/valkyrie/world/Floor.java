package com.smokebox.valkyrie.world;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.smokebox.lib.utils.Intersect;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.lib.utils.geom.Circle;
import com.smokebox.lib.utils.geom.Rectangle;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.Interactable;
import com.smokebox.valkyrie.actor.ability.CollidesWorld;
import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;

import java.util.ArrayList;

public class Floor {
	
	static enum RoomType {
		REGULAR,
		BOSS,
		TREASURE
	}

	public static enum FloorType {
		TESTING_GROUNDS,
		LOST_CITY
	}

	private ArrayList<Room> rooms;
	private Room activeRoom;
	
	Game game;

    private Array<RectangleMapObject> collisionRects;
	
	public Floor(FloorType type, int size, Game game) {
		rooms = new ArrayList<Room>();
		this.game = game;

		System.out.println(type);
		this.rooms = FloorGeneration.getTestArena();
		setRoom(rooms.get(0));
	}
	
	public void setRoom(Room r) {
		activeRoom = r;
        game.setMap(r.map);
        collisionRects = r.getCollisionRects();
	}
	
	public void draw(TiledMapRenderer tmr) {
		activeRoom.draw(tmr);
	}
	
	public void drawCollision(ShapeRenderer sr) {
		activeRoom.drawCollision(sr);
	}
	
	public void worldCollision(CollidesWorld col) {
        Rectangle hb = col.getBoundingBox();
        Vector2 pos = col.getPos();
        Vector2 vel = col.getVel();

        for(RectangleMapObject l : collisionRects) {
            com.badlogic.gdx.math.Rectangle r = l.getRectangle();
            Vector2 pen = new Vector2(Intersect.horisontalPenetrationRectRect(hb.x, r.x, hb.width, r.width),
                    Intersect.verticalPenetrationRectRect(hb.y, r.y, hb.height, r.height));
			if(Math.abs(pen.x) > Math.abs(pen.y)) pen.x = 0;
			else pen.y = 0;
			if(pen.y != 0) {
				if(pen.y > 0) col.setGrounded(true);
				vel.y = 0;
			} else if(pen.x != 0 && (Math.signum(pen.x) != Math.signum(vel.x)))
				vel.x = 0;
			pos.add(pen);
        }
    }

	public boolean collidesWithWorld(float x, float y, float radius) {
		for(RectangleMapObject l : collisionRects) {
			com.badlogic.gdx.math.Rectangle r = l.getRectangle();
			if(Intersect.rectCircle(new Circle(x, y, radius), new Rectangle(r.x, r.y, r.width, r.height))) return true;
		}
		return false;
	}

	public ArrayList<Interactable> getInteractables(PlayableCharacter player) {
		return activeRoom.getInteractables(player);
	}

	public float getDistanceToGround(Vector2 pos) {
		float shortest = 32; // max length
		for(RectangleMapObject rect : collisionRects) {
			com.badlogic.gdx.math.Rectangle r = rect.getRectangle();
			if(pos.x < r.x || pos.x > r.x + r.width || pos.y < r.y + r.height) continue;
			float dist = pos.y - (r.y + r.height);
			if(dist < shortest) shortest = dist;
		}
		return shortest;
	}
}