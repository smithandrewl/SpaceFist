package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
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

    public void LoadContent() {
        Rectangle resolution = gameData.getResolution();

        overlayTexture = gameData.getTextures().get("TitleScreen");

        overlayRect = new Rectangle(
            0,
            0,
            resolution.getWidth(),
            resolution.getHeight()
        );
    }

    public void EnteringState() {
        enteredAt = new Date();
    }

    public void Update() {
        long timeDiff = new Date().getTime() - enteredAt.getTime();

        if ((timeDiff / 1000) > 3) {
            gameData.setCurrentState(gameData.getMenuState());
        }

        // This waits 300 milliseconds after the splash screen state has been entered
        // before processing input.
        else if (timeDiff > 300) {
            if (Gdx.input.isKeyPressed(Input.Keys.ENTER) ||
                Gdx.input.isKeyPressed(Input.Keys.SPACE) ||
                Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                gameData.setCurrentState(gameData.getMenuState());
            }

            if (Gdx.input.isTouched()) {
                gameData.setCurrentState(gameData.getMenuState());
            }
        }
    }

    public void Draw() {
        Rectangle resolution = gameData.getResolution();

        gameData.getSpriteBatch().draw(
            gameData.getTextures().get("Background"),
            0,
            0,
            resolution.getWidth(),
            resolution.getHeight()
        );

        gameData.getSpriteBatch().draw(
            overlayTexture,
            0,
            0,
            overlayRect.getWidth(),
            overlayRect.getHeight()
        );
    }

    public void ExitingState() { }
}

