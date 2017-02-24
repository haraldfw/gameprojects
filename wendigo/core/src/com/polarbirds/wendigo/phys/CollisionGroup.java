package com.polarbirds.wendigo.phys;

/**
 * Created by Harald on 30.08.2016.
 */
public enum CollisionGroup {
    NONE(new boolean[]{false, false, false}),
    PLAYER_NORMAL(new boolean[]{false, true, true}),
    TILE_SOLID(new boolean[]{false, true, true});

    private boolean[] collidesWith;

    CollisionGroup(boolean[] collidesWith) {
        if (collidesWith.length < CollisionGroup.values().length) {
            throw new IllegalArgumentException(
                    "Length of collidesWith array must be: " + CollisionGroup.values().length);
        }
        this.collidesWith = collidesWith;
    }

    public boolean doesContact(CollisionGroup b) {
        return collidesWith[b.ordinal()];
    }
}
