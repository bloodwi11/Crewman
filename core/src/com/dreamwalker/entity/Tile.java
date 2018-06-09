package com.dreamwalker.entity;

import com.badlogic.gdx.utils.Pool;
import com.dreamwalker.config.GameConfig;

public class Tile extends GameObjectBase implements Pool.Poolable {

    private int spriteSheetIndex = 0;
    private int tileIndex = 0;

    public Tile() {
        super(GameConfig.OBJECT_WIDTH, GameConfig.OBJECT_HEIGHT);
    }

    @Override
    public void reset() {

    }

    public int getSpriteSheetIndex() {
        return spriteSheetIndex;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void setSpriteSheetIndex(int index) {
        this.spriteSheetIndex = index;
    }

    public void setTileIndex(int index) {
        this.tileIndex = index;
    }
}
