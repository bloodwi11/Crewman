package com.dreamwalker;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.dreamwalker.screen.loading.LoadingScreen;

public class DreamWalkerGame extends Game {

    private static final Logger log = new Logger (DreamWalkerGame.class.getName(), Logger.DEBUG);

    private AssetManager assetManager;
    private SpriteBatch batch;

    @Override
    public void create() {
        log.info("DreamWalkerGame.create()");
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        batch = new SpriteBatch();

        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        log.info("DreamWalkerGame.dispose()");
        assetManager.dispose();
        batch.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
