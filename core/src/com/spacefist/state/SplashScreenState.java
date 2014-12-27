package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

import java.util.Date;

/**
 * When the game is in the splash screen state
 * it draws the splash screen and waits for the player to press enter or click the mouse.
 *
 * When the player presses enter the game switchs to the menu state.
 */
public class SplashScreenState implements GameState {
    GameData  gameData;
    Texture   overlayTexture;
    Rectangle overlayRect;
    Date      enteredAt;

    /**
     * Creates a new SplashScreenState instance.
     *
     * @param gameData Common game data
     */
    public SplashScreenState(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void loadContent() {
        Rectangle resolution = gameData.getResolution();

        overlayTexture = gameData.getTextures().get("TitleScreen");

        overlayRect = new Rectangle(
            0,
            0,
            resolution.getWidth(),
            resolution.getHeight()
        );
    }

    @Override
    public void enteringState() {
        enteredAt = new Date();
    }

    @Override
    public void update() {
        long timeDiff = new Date().getTime() - enteredAt.getTime();

        if ((timeDiff / 1000) > 3) {
            gameData.setCurrentState(gameData.getMenuState());
        }

        // This waits 300 milliseconds after the splash screen state has been entered
        // before processing input.
        else if (timeDiff > 300) {
            if (Gdx.input.isKeyPressed(Keys.ENTER) ||
                Gdx.input.isKeyPressed(Keys.SPACE) ||
                Gdx.input.isKeyPressed(Keys.ESCAPE)) {
                gameData.setCurrentState(gameData.getMenuState());
            }

            if (Gdx.input.isTouched()) {
                gameData.setCurrentState(gameData.getMenuState());
            }
        }
    }

    @Override
    public void draw() {
        Rectangle resolution    = gameData.getResolution();
        SpriteBatch spriteBatch = gameData.getSpriteBatch();

        spriteBatch.draw(
            gameData.getTextures().get("Background"),
            0,
            0,
            resolution.getWidth(),
            resolution.getHeight()
        );

        spriteBatch.draw(
            overlayTexture,
            0,
            0,
            overlayRect.getWidth(),
            overlayRect.getHeight()
        );
    }

    @Override
    public void exitingState() { }
}
