package com.dreamwalker.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.dreamwalker.DreamWalkerGame;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private Preferences PREFS;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(DreamWalkerGame.class.getSimpleName());
    }

    private boolean showGrid = true;

    public void updateShowGrid() {
        showGrid = !showGrid;
    }

    public boolean isGridEnabled() {
        return showGrid;
    }
}
