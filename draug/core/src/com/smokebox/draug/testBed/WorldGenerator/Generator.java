package com.smokebox.draug.testBed.WorldGenerator;

import com.smokebox.lib.pcg.roomBased.RoomDef;
import com.smokebox.lib.utils.geom.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Harald Wilhelmsen on 11/13/2014.
 */

public class Generator {

	private static Room[] convertFromDefs(RoomDef[] defs) {
		Room[] rooms = new Room[defs.length];
		for(int i = 0; i < defs.length; i++) {
			rooms[i] = new Room(defs[i]);
		}



		return rooms;
	}

	/**
	 * Generates an array of Rooms
	 * @param amountOfRooms	How many rooms to place, *not* including all special rooms.
	 * @param specialRooms	Array of all special rooms to place
	 * @param chanceForLargeRooms	Between 0 and 1. 0 will ensure all rooms stay 1by1. 1 will make all rooms maxWidthbymaxHeight
	 * @param maxWidth	The max width of rooms
	 * @param maxHeight	The max height of rooms
	 * @return	The generated map in a Room-array.
	 */
	public static RoomDef[] generateArray(int amountOfRooms, RoomType[] specialRooms, float chanceForLargeRooms,
									   int maxWidth, int maxHeight, Random rng) {
		ArrayList<RoomDef> roomsList = new ArrayList<RoomDef>();
		return null;
	}

	/**
	 * Places the given room in the map. NB: Boss-rooms should be placed first!
	 * @param roomToPlace	The room to place in the map.
	 * @param roomDefs	The array of rooms already placed
	 * @param endRooms	A list of coordinates for the end-rooms in the rooms-array
	 * @return True of the placement was successful, false if not.
	 */
	private static boolean placeSpecialRoom(RoomType roomToPlace, RoomDef[] roomDefs, ArrayList<Integer> endRooms, Random rng) {
		if(endRooms.isEmpty()) return false;
		switch(roomToPlace) {
			case BOSS: {
				int[] lengths = new int[endRooms.size()];
				for(int i = 0; i < lengths.length; i++) {
					// find length of Room endRooms[i] and put in lengths[i]
				}
				// choose longest length
				// if multiple lengths are the same. Use rng to choose a random one
				break;
			}
			case TREASURE: {
				int endRoomUsed = rng.nextInt(endRooms.size());
//				roomDefs[endRooms.get(endRoomUsed)].setType(RoomType.TREASURE);
				endRooms.remove(endRoomUsed);
				break;
			}
			case SECRET: {

				break;
			}
			case SHOP: {
				int endRoomUsed = rng.nextInt(endRooms.size());
//				roomDefs[endRooms.get(endRoomUsed)].setType(RoomType.SHOP);
				endRooms.remove(endRoomUsed);
				break;
			}
		}
		return true;
	}

	private RoomDef[][] crop(RoomDef[][] roomDefs) {
		int leftBound = -1;
		int topBound = -1;
		int rightBound = -1;
		int botBound = -1;

		for(int i = 0; i < roomDefs.length; i++) {
			if(leftBound == -1) { // if leftBound is not found, look for it
				for(int j = 0; j < roomDefs[0].length; j++) {
					if(roomDefs[i][j] != null) {
						leftBound = i - 1;
						break;
					}
				}
			} else { // leftBound is found, the first null-column is now rightBound
				boolean thisIsRightBound = true;
				for(int j = 0; j < roomDefs[0].length; j++) {
					if(roomDefs[i][j] != null) thisIsRightBound = false;
				}
				if(thisIsRightBound) rightBound = i;
			}
		}

		for(int j = 0; j < roomDefs.length; j++) {
			if(botBound == -1) { // if botBound is not found, look for it
				for(int i = 0; i < roomDefs.length; i++) {
					if(roomDefs[i][j] != null) {
						botBound = i - 1;
						break;
					}
				}
			} else { // botBound is found, the first null-column is now rightBound
				boolean thisIsTopBound = true;
				for(int i = 0; i < roomDefs.length; i++) {
					if(roomDefs[i][j] != null) thisIsTopBound = false;
				}
				if(thisIsTopBound) topBound = j;
			}
		}



		return null;
	}

	public class WorldWithMinimap {

		public final RoomDef[] roomDefs;
		public final Rectangle[] miniMap;

		public WorldWithMinimap(RoomDef[] rooms, int[] xs, int[] ys, int[]ws, int[] hs) {
			this.roomDefs = rooms;
			miniMap = new Rectangle[rooms.length];

			for(int i = 0; i < rooms.length; i++) {
				miniMap[i] = new Rectangle(xs[i], ys[i], rooms[i].width, rooms[i].height);
			}

		}
	}
}
