package com.dreamwalker.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.dreamwalker.config.GameConfig;

public abstract class GameObjectBase {

    private float x;
    private float y;
    private float width = GameConfig.OBJECT_WIDTH;
    private float height = GameConfig.OBJECT_HEIGHT;

    private Rectangle bounds;

    public GameObjectBase(float width, float height) {
        bounds = new Rectangle(x, y, width, height);
    }

    public void drawDebug(ShapeRenderer renderer) {
        renderer.rect(bounds.x - bounds.width / 2, bounds.y - bounds.height / 2, bounds.width, bounds.height);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void updateBounds() {
        float halfWidth = width / 2.0f;
        float halfHeight = height / 2.0f;
        bounds.setPosition(x + halfWidth, y + halfHeight);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
