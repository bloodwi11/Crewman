package com.dreamwalker.screen.game.camera;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.dreamwalker.common.GameManager;

import static com.dreamwalker.config.GameConfig.*;

public class GameCamera {

    private static final Logger log = new Logger(GameCamera.class.getName(), Logger.DEBUG);

    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();

    private GameCameraConfig config;

    // Constructor
    public GameCamera() {
        config = new GameCameraConfig();
        log.info("cameraConfig= " + config);
    }

    // Public methods
    public void setStartPosition(float x, float y) {
        startPosition.set(x, y);
        position.set(x, y);
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.set(position, 0);
        camera.update();
    }

    public void gameInput(float delta) {
        // check if we are not on desktop then don't handle input just return
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }

        float cameraX = position.x - WORLD_CENTER_X;
        float cameraY = position.y - WORLD_CENTER_Y;

        float moveSpeed = CAMERA_MOVE_SPEED * delta;

        // move controls
        if (config.isLeftPressed()) {
            if (cameraX > 0.0f) {
                if (cameraX - moveSpeed < 0.0f) {
                    setPosition(WORLD_CENTER_X, position.y);
                } else {
                    moveLeft(moveSpeed);
                }
            }
        } else if (config.isRightPressed()) {
            if (cameraX < WORLD_EDGE_X) {
                if (cameraX + moveSpeed > WORLD_EDGE_X) {
                    setPosition(WORLD_CENTER_X + WORLD_EDGE_X, position.y);
                } else {
                    moveRight(moveSpeed);
                }
            }
        } else if (config.isUpPressed()) {
            if (cameraY < 0.0f) {
                if (cameraY + moveSpeed > 0.0f) {
                    setPosition(position.x,WORLD_CENTER_Y);
                } else {
                    moveUp(moveSpeed);
                }
            }
        } else if (config.isDownPressed()) {
            if (cameraY > -WORLD_EDGE_Y) {
                if (cameraY - moveSpeed < -WORLD_EDGE_Y) {
                    setPosition(position.x, WORLD_CENTER_Y - WORLD_EDGE_Y);
                } else {
                    moveDown(moveSpeed);
                }
            }
        }

        if (config.isCenterPressed()) {
            centerCamera();
        }

        if (config.isGridPressed()) {
            GameManager.INSTANCE.updateShowGrid();
        }
    }

    // == Private Methods ==
    private void setPosition(float x, float y) {
        position.set(x, y);
    }

    private void moveCamera(float xSpeed, float ySpeed) {
        setPosition(position.x + xSpeed, position.y + ySpeed);
    }

    private void centerCamera() {
        setPosition(WORLD_CENTER_X + WORLD_EDGE_X / 2,WORLD_CENTER_Y - WORLD_EDGE_Y / 2.0f);
    }

    private void moveLeft(float speed) {
        moveCamera(-speed, 0);
    }

    private void moveRight(float speed) {
        moveCamera(speed, 0);
    }

    private void moveUp(float speed) {
        moveCamera(0, speed);
    }

    private void moveDown(float speed) {
        moveCamera(0, -speed);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }
}
