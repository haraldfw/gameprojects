package com.smokebox.valkyrie.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.lib.utils.geom.Rectangle;
import com.smokebox.valkyrie.Game;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 9/20/2014.
 */
public class FloorGeneration {
	/**
	 * Generates a floor of the specified type with the specified amount of rooms
	 * @param type	Type of area(eg lost city etc...
	 * @param size	Amount of rooms to put in the floor.
	 * @return	The generated map
	 */
	public static ArrayList<Room> generateMeSomeFloorPl3x(Floor.FloorType type, int size, Game game) {
		ArrayList<TiledMap> maps = getMaps(game.getAssetManager());
		// generating...
		// generating...
		// generating...
		// generating...
		// ... ding!
		return null;
	}

	public static ArrayList<Room> getTestArena() {
		ArrayList<Room> map = new ArrayList<>();
		map.add(new Room(null, new TmxMapLoader().load("data/env/testArena.tmx")));
		return map;
	}

	/**
	 * Loads the tilemaps used by the generator
	 * @param type	The FloorType of the map
	 * @param astmng	AssetManager for the game
	 */
	public static void loadMapFromFloorType(Floor.FloorType type, AssetManager astmng) {
		FileHandle dir = Gdx.files.internal("data/env/" + type.toString().toLowerCase());
		astmng.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		for(FileHandle entry : dir.list()) {
			// entry is not a map, do not handle
			if(entry.isDirectory() || !entry.extension().contains("tmx")) continue;
			astmng.load(entry.toString(), TiledMap.class);
		}
	}

	/**
	 * Gets all maps from Asset Manager and puts them in a list.
	 * @param astmng	The AssetManager having the maps
	 * @return
	 */
	private static ArrayList<TiledMap> getMaps(AssetManager astmng) {
		// parse files to tiledmaps and return list of them
		ArrayList<TiledMap> maps = new ArrayList<TiledMap>();
		Array mapNames = astmng.getAssetNames();
		for(int i = 0; i < mapNames.size; i++) {
			String s = mapNames.get(i).toString();
			if(s.contains(".tmx")) maps.add(astmng.get(s));
		}
		return maps;
	}

	private class RectWithDoors {
		public Rectangle rect;
		public ArrayList<Vector2> doors;

		public RectWithDoors(TiledMap map) {

		}
	}
}
