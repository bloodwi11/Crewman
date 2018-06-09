package com.dreamwalker.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.dreamwalker.DreamWalkerGame;
import com.dreamwalker.entity.Tile;
import com.dreamwalker.util.NoiseUtils;

import static com.dreamwalker.config.GameConfig.*;

/* Created by Bloodwi11 on 10/30/2016. */
public class GameController {

    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);

    // == attributes ==
    private Pool<Tile> tilePool;
    private Array<Tile> board;

    private final DreamWalkerGame game;
    private final AssetManager assetManager;

    // == constructors ==
    public GameController(DreamWalkerGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        init();
    }

    // == init ==
    private void init() {
        tilePool = Pools.get(Tile.class);
        board = new Array<Tile>();
        // MAP GEN GOES HERE
        createMap();
    }

    private void createMap() {
        boolean[][] cellMap = new boolean[MAP_WIDTH][MAP_HEIGHT];

        // Generate Map from demo template (All tiles set to true)
        cellMap = initialiseDemoMap(cellMap);
        manualPopulateMap(cellMap);
        // Generate Map with Perlin noise (Random true/false tilemap)
        //cellMap = initialiseMap(cellMap);

        // Used for trimming tiles (Adds & removes tiles to smooth edges), Still broken?
        /*
        for (int i = 0; i < CYCLES; i++) {
            cellMap = doSimulationStep(cellMap);
        }*/
        //populateMap(cellMap);
    }

    private void manualPopulateMap(boolean[][] cellMap) {
        for (int x = 0; x < MAP_WIDTH; x++) {
            for (int y = 0; y < MAP_HEIGHT; y++) {
                if (cellMap[x][y]) {
                    Tile tile = tilePool.obtain();
                    tile.setPosition(x, y);
                    tile.setSpriteSheetIndex(2);
                    if (tile.getSpriteSheetIndex() > 0) {
                        tile.setTileIndex(tileNeighboring(cellMap, x, y));
                    } else {
                        log.debug("No Tileset found! Using NULL Tile.");
                    }
                    board.add(tile);
                }
            }
        }
    }

    private void populateMap(boolean[][] cellMap) {
        for (int x = 0; x < MAP_WIDTH; x++) {
            for (int y = 0; y < MAP_HEIGHT; y++) {
                if (cellMap[x][y]) {
                    Tile tile = tilePool.obtain();
                    tile.setPosition(x, y);
                    tile.setSpriteSheetIndex(2);
                    if (tile.getSpriteSheetIndex() > 0) {
                        tile.setTileIndex(tileNeighboring(cellMap, x, y));
                    } else {
                        log.debug("No Tileset found! Using NULL Tile.");
                    }
                    board.add(tile);
                }
            }
        }
    }

    private int tileNeighboring(boolean[][] cellMap, int x, int y) {
        int[][] offset = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        int count = 0;
        for (int i = 0; i < offset.length; i++) {
            int neighborX = x + offset[i][0];
            int neighborY = y + offset[i][1];
            if (neighborX < 0 || neighborY < 0 || neighborX >= MAP_WIDTH || neighborY >= MAP_HEIGHT) {
                count++;
            } else if (cellMap[neighborX][neighborY]) {
                count++;
            }
        }
        return 0;
        //return count;
    }

    private boolean[][] initialiseDemoMap(boolean[][] map) {
        for (int x = 0; x < MAP_WIDTH; x++) {
            for (int y = 0; y < MAP_HEIGHT; y++) {
                map[x][y] = true;
            }
        }
        return map;
    }

    private boolean[][] initialiseMap(boolean[][] map) {
        float noise[][] = NoiseUtils.generatePerlinNoise(NoiseUtils.generateWhiteNoise(MAP_WIDTH, MAP_HEIGHT), OCTAVE_COUNT);
        for (int x = 0; x < MAP_WIDTH; x++) {
            for (int y = 0; y < MAP_HEIGHT; y++) {
                if (noise[x][y] <= MAX_TILE_HEIGHT) {
                    map[x][y] = true;
                }
            }
        }
        return map;
    }

    private int countLivingNeighbors(boolean[][] map, int x, int y) {
        int[][] offset = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        int count = 0;
        for (int i = 0; i < offset.length; i++) {
            int neighborX = x + offset[i][0];
            int neighborY = y + offset[i][1];
            if (neighborX < 0 || neighborY < 0 || neighborX >= MAP_WIDTH || neighborY >= MAP_HEIGHT) {
                count++;
            } else if (map[neighborX][neighborY]) {
                count++;
            }
        }
        return count;
    }

    private boolean[][] doSimulationStep(boolean[][] oldMap) {
        boolean[][] newMap = new boolean[MAP_WIDTH][MAP_HEIGHT];
        for (int x = 0; x < MAP_WIDTH; x++) {
            for (int y = 0; y < MAP_HEIGHT; y++) {
                int neighbors = countLivingNeighbors(oldMap, x, y);
                if (oldMap[x][y]) {
                    if (neighbors < DEATH_LIMIT) {
                        newMap[x][y] = false;
                    } else {
                        newMap[x][y] = true;
                    }
                } else {
                    if (neighbors > BIRTH_LIMIT) {
                        newMap[x][y] = true;
                    } else {
                        newMap[x][y] = false;
                    }
                }
            }
        }
        return newMap;
    }

    public void update(float delta) {
    }

    public Array<Tile> getMap() {
        return board;
    }
}
