package com.dreamwalker.config;

import com.badlogic.gdx.Input;

public class GameConfig {

    public static final int MAP_WIDTH = 3;
    public static final int MAP_HEIGHT = 3;

    public static final float WIDTH = 800.0f; // pixels
    public static final float HEIGHT = 480.0f; // pixels

    public static final float HUD_WIDTH = 800.0f; // World Units
    public static final float HUD_HEIGHT = 480.0f; // World Units

    public static final float WORLD_WIDTH = 50.0f; // world units
    public static final float WORLD_HEIGHT = 30.0f; // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2.0f; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2.0f; // world units
    public static final float WORLD_EDGE_X = ((MAP_WIDTH + MAP_HEIGHT) - WORLD_WIDTH);// world units
    public static final float WORLD_EDGE_Y = (MAP_WIDTH + MAP_HEIGHT) / 2.0f - WORLD_HEIGHT;// world units

    public static final float OBJECT_WIDTH = 2.0f;
    public static final float OBJECT_HEIGHT = 1.0f;

    public static final float UNIT_RATIO = 16.0f;

    public static final boolean DEBUG = false;

    // Map Gen
    public static final long SEED = 1;
    public static final int OCTAVE_COUNT = 3;
    public static final int DEATH_LIMIT = 2;
    public static final int BIRTH_LIMIT = 2;
    public static final int CYCLES = 4;
    public static final float MAX_TILE_HEIGHT = 0.5f;

    public static final float CAMERA_MOVE_SPEED = 20.0f;
    public static final String MAX_ZOOM_IN = "maxZoomIn";
    public static final String MAX_ZOOM_OUT = "maxZoomOut";
    public static final String MOVE_SPEED = "moveSpeed";
    public static final String ZOOM_SPEED = "zoomSpeed";


    public static final String LEFT_KEY = "leftKey";
    public static final String RIGHT_KEY = "rightKey";
    public static final String UP_KEY = "upKey";
    public static final String DOWN_KEY = "downKey";

    public static final String ZOOM_IN_KEY = "zoomInKey";
    public static final String ZOOM_OUT_KEY = "zoomOutKey";

    public static final int DEFAULT_LEFT_KEY = Input.Keys.LEFT;
    public static final int DEFAULT_RIGHT_KEY = Input.Keys.RIGHT;
    public static final int DEFAULT_UP_KEY = Input.Keys.UP;
    public static final int DEFAULT_DOWN_KEY = Input.Keys.DOWN;

    public static final int DEFAULT_ZOOM_IN_KEY = Input.Keys.PLUS;
    public static final int DEFAULT_ZOOM_OUT_KEY = Input.Keys.MINUS;

    public static final int DEFAULT_GRID_KEY = Input.Keys.G;
    public static final int DEFAULT_CENTER_KEY = Input.Keys.SPACE;

    public static final float DEFAULT_MOVE_SPEED = 20.0f;
    public static final float DEFAULT_ZOOM_SPEED = 2.0f;
    public static final float DEFAULT_MAX_ZOOM_IN = 0.50f;
    public static final float DEFAULT_MAX_ZOOM_OUT = 2.0f;

    private GameConfig() {}
}
