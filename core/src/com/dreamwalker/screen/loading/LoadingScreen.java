package com.dreamwalker.screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dreamwalker.DreamWalkerGame;
import com.dreamwalker.assets.AssetDescriptors;
import com.dreamwalker.config.GameConfig;
import com.dreamwalker.screen.game.GameScreen;
import com.dreamwalker.util.GdxUtils;

public class LoadingScreen extends ScreenAdapter {

    // == constants ==
    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2.0f; // world units
    private static final float PROGRESS_BAR_HEIGHT = 60.0f; // world units

    // == attributes ==
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    private final DreamWalkerGame game;
    private final AssetManager assetManager;

    // == constructor ==
    public LoadingScreen(DreamWalkerGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // == public methods ==
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(com.dreamwalker.config.GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        assetManager.load(AssetDescriptors.TILE_GRID);
        assetManager.load(AssetDescriptors.NULL_TILE);
        assetManager.load(AssetDescriptors.GLASS_TILE);
        assetManager.load(AssetDescriptors.PLATE_TILE);
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        if (changeScreen) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        // NOTE: Screens don't dispose automatically
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    // == Private Methods ==
    private void update(float delta) {

        // progress is between 0 and 1
        progress = assetManager.getProgress();

        // update returns true when all assets are loaded
        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        float progressBarX = (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2.0f;
        float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2.0f;

        renderer.rect(progressBarX, progressBarY,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT
        );
    }
}
