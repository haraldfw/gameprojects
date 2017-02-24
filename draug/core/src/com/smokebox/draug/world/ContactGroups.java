package com.smokebox.draug.world;

import com.badlogic.gdx.physics.box2d.Filter;

/**
* Created by Harald Wilhelmsen on 10/13/2014.
*/
public enum ContactGroups {
	WORLD(),
	PLAYER(),
	ENEMY(),
	FRIENDLY_BULLET(),
	FRIENDLY_BOLT(),
	ENEMY_BULLET(),
	FRIENDLY_PARTICLE(),
	ENEMY_PARTICLE();

	public final Filter filter = new Filter();

	ContactGroups() {
		filter.groupIndex = (short) this.ordinal();
	}

	public static boolean[][] matrix = {//	World	PLAYER	ENEMY	FriBU	FriBO	EnmB	FriPa	EnmP
										{	false,	true,	true,	true,	true,	true,	true,	true	},	// WORLD
										{	true,	false,	true,	false,	false,	true,	false,	true	},	// PLAYER
										{	true,	true,	true,	true,	true,	false,	true,	false	},	// ENEMY
										{	true,	false,	true,	false,	false,	false,	true,	true	},	// FRIENDLY_BULLET
										{	true,	false,	true,	false,	false,	false,	true,	true	},	// FRIENDLY_BOLT
										{	true,	true,	false,	false,	false,	false,	true,	true	},	// ENEMY_BULLET
										{	true,	false,	true,	true,	true,	true,	false,	true	},	// FRIENDLY_PARTICLE
										{	true,	true,	false,	true,	true,	true,	true,	false	}	// ENEMY_PARTICLE
							};
}
