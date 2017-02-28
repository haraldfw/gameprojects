package com.wilhelmsen.exile.screen;


/**
 * Created by Harald on 28.02.2017.
 */
public enum Aspect {
    // 16:9. 1920x1080, 1280x720...
    sixteenNine(16f / 9f, 32, 18),
    // 16:10, 1920x1200...
    sixteenTen(16f / 10f, 32, 20),
    // 4:3. 1440x1080...
    fourThree(4f / 3f, 27, 20.25f),
    // 4K
    fourK(256f / 135f, 34.13333333333333f, 18);

    public final float aspect;
    public final float frustumWidth;
    public final float frustumHeight;

    Aspect(float aspect, float frustumWidth, float frustumHeight) {
        this.aspect = aspect;
        this.frustumWidth = frustumWidth;
        this.frustumHeight = frustumHeight;
    }

    public static Aspect getFromAspect(float aspect) {
        for (Aspect a : Aspect.values()) {
            if (aspect == a.aspect) {
                return a;
            }
        }
        throw new IllegalArgumentException("Illegal aspect ratio");
    }
}
