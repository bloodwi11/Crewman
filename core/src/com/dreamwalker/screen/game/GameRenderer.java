package com.dreamwalker.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dreamwalker.assets.AssetDescriptors;
import com.dreamwalker.assets.RegionNames;
import com.dreamwalker.common.GameManager;
import com.dreamwalker.config.GameConfig;
import com.dreamwalker.entity.Tile;
import com.dreamwalker.screen.game.camera.GameCamera;
import com.dreamwalker.util.GdxUtils;
import com.dreamwalker.util.ViewportUtils;

import java.text.DecimalFormat;

import static com.dreamwalker.config.GameConfig.*;

/* Created by Bloodwi11 on 10/30/2016. */
public class GameRenderer implements Disposable {

    private static final Logger log = new Logger(GameRenderer.class.getName(), Logger.DEBUG);

    // == attributes ==
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private BitmapFont font = new BitmapFont();
    private final GlyphLayout layout = new GlyphLayout();
    private com.dreamwalker.screen.game.camera.GameCamera gameCameraController;
    private final GameController controller;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private TextureRegion[][] tileMap;
    private Texture gridTexture;


    // == constructors ==
    public GameRenderer(SpriteBatch batch, AssetManager assetManager, GameController controller) {
        this.batch = batch;
        this.assetManager = assetManager;
        this.controller = controller;
        init();
    }

    // == init ==
    private void init() {
        camera = new OrthographicCamera();
        // Game Camera
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        // Hud Camera
        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);

        // Debug Camera Controller
        gameCameraController = new GameCamera();
        gameCameraController.setStartPosition(WORLD_CENTER_X + WORLD_EDGE_X / 2, WORLD_CENTER_Y - WORLD_EDGE_Y / 2.0f);

        tileMap = new TextureRegion[3][];

        // NULL Tile
        tileMap[0] = new TextureRegion[1];
        // GLASS Tile
        tileMap[1] = new TextureRegion[20];
        // Plate Tile
        tileMap[2] = new TextureRegion[1];

        TextureAtlas nullTileAtlas = assetManager.get(AssetDescriptors.NULL_TILE);
        TextureAtlas glassTile = assetManager.get(AssetDescriptors.GLASS_TILE);
        TextureAtlas plateTile = assetManager.get(AssetDescriptors.PLATE_TILE);
        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap[x].length; y++) {
                if (x == 0) {
                    tileMap[x][y] = nullTileAtlas.findRegion(RegionNames.TILE_LABEL + y);
                }
                if (x == 1) {
                    tileMap[x][y] = glassTile.findRegion(RegionNames.TILE_LABEL + y);
                }
                if (x == 2) {
                    tileMap[x][y] = plateTile.findRegion(RegionNames.TILE_LABEL + y);
                }
            }
        }
        gridTexture = assetManager.get(AssetDescriptors.TILE_GRID);
    }

    // == public methods ==
    public void render(float delta) {
        // not wrapping inside alive, due to being able control camera even during game over
        gameCameraController.gameInput(delta);
        gameCameraController.applyTo(camera);

        // clear screen
        GdxUtils.clearScreen();

        // render game board
        renderGamePlay();

        // render ui/hud
        renderUI();

        // render debug graphics
        renderDebug();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private void renderGamePlay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawTiles();

        batch.end();
    }

    // == private methods ==
    private void drawTiles() {
        for (Tile tile : controller.getMap()) {
            float mouseX = Gdx.input.getX() + ((camera.position.x - WORLD_CENTER_X) * UNIT_RATIO);
            float mouseY = Gdx.input.getY() - ((camera.position.y - WORLD_CENTER_Y) * UNIT_RATIO);
            if (ViewportUtils.clamp(ViewportUtils.getBoardX(mouseX, mouseY), 0, MAP_WIDTH - 1) == tile.getX() &&
                    ViewportUtils.clamp(ViewportUtils.getBoardY(mouseX, mouseY), 0, MAP_HEIGHT - 1) == tile.getY()) {
                Color oldColor = batch.getColor();
                batch.setColor(Color.RED);
                batch.draw(tileMap[tile.getSpriteSheetIndex()][tile.getTileIndex()],
                        getXOffset(tile.getX(), tile.getY()), getYOffset(tile.getX(), tile.getY()),
                        tile.getWidth(), tile.getHeight());
                batch.setColor(oldColor);
            } else {
                batch.draw(tileMap[tile.getSpriteSheetIndex()][tile.getTileIndex()],
                        getXOffset(tile.getX(), tile.getY()), getYOffset(tile.getX(), tile.getY()),
                        tile.getWidth(), tile.getHeight());
            }
            if (GameManager.INSTANCE.isGridEnabled()) {
                batch.draw(gridTexture,
                        getXOffset(tile.getX(), tile.getY()), getYOffset(tile.getX(), tile.getY()),
                        tile.getWidth(), tile.getHeight());
            }
        }
    }

    private float getXOffset(float x, float y) {
        return (x - y) + (MAP_HEIGHT - 1);
    }

    private float getYOffset(float x, float y) {
        return WORLD_HEIGHT - 1 - (x + y) / 2;
    }

    private void renderUI() {
        hudViewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        //Draw

        batch.end();
    }

    private void renderDebug() {
        viewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        if (DEBUG) {
            DecimalFormat dF = new DecimalFormat();
            dF.setMaximumFractionDigits(2);

            debugLine("World(Map) Width: " + WORLD_WIDTH + "(" + MAP_WIDTH + ")", 0);
            debugLine("World(Map) Height: " + WORLD_HEIGHT + "(" + MAP_HEIGHT + ")", 1);
            debugLine("TileX: " + ViewportUtils.clamp(ViewportUtils.getBoardX(Gdx.input.getX() + ((camera.position.x - WORLD_CENTER_X) * UNIT_RATIO), Gdx.input.getY() - ((camera.position.y - WORLD_CENTER_Y) * UNIT_RATIO)), 0, GameConfig.MAP_WIDTH - 1), 2);
            debugLine("TileY: " + ViewportUtils.clamp(ViewportUtils.getBoardY(Gdx.input.getX() + ((camera.position.x - WORLD_CENTER_X) * UNIT_RATIO), Gdx.input.getY() - ((camera.position.y - WORLD_CENTER_Y) * UNIT_RATIO)), 0, GameConfig.MAP_HEIGHT - 1), 3);
            debugLine("CursorX: " + Gdx.input.getX() / UNIT_RATIO, 4);
            debugLine("CursorY: " + Gdx.input.getY() / UNIT_RATIO, 5);
            debugLine("CameraX: " + dF.format(gameCameraController.getX() - WORLD_CENTER_X), 6);
            debugLine("CameraY: " + dF.format(gameCameraController.getY() - WORLD_CENTER_Y), 7);
            debugLine("Test: " + ((MAP_WIDTH + MAP_HEIGHT) / 2.0f - WORLD_HEIGHT), 8);
            //debugLine("Cycles: " + CYCLES, 8);
            //debugLine("Octaves: " + OCTAVE_COUNT, 9);
            //debugLine("Seed: " + SEED, 10);
        }

        batch.end();
    }

    private void debugLine(String label, float offset) {
        layout.setText(font, label);
        font.draw(batch, label,
                20,
                HUD_HEIGHT - layout.height - (offset * 20)
        );
    }
}
