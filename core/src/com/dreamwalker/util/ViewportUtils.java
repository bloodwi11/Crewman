package com.dreamwalker.util;

import static com.dreamwalker.config.GameConfig.MAP_HEIGHT;
import static com.dreamwalker.config.GameConfig.UNIT_RATIO;

/* Created by Bloodwi11 on 10/19/2016. */
public class ViewportUtils {

    public static float screenToWorldX(float x, float y) {
        return (x / UNIT_RATIO + y / (UNIT_RATIO / 2.0f)) / 2.0f;
    }

    public static float screenToWorldY(float x, float y) {
        return (y / (UNIT_RATIO / 2.0f) - (x / UNIT_RATIO)) / 2.0f;
    }

    public static float getBoardX(float x, float y) {
        return (float) Math.floor(screenToWorldX(x, y) - MAP_HEIGHT / 2.0f);
    }

    public static float getBoardY(float x, float y) {
        return (float) Math.floor(screenToWorldY(x, y) + MAP_HEIGHT / 2.0f);
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    private ViewportUtils() {
    }
}
