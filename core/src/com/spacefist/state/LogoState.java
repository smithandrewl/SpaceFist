package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

import java.util.Date;

/**
 * This state displays the team logo before switching
 * to the splash screen.
 */
public class LogoState implements GameState {
    private static final int loadTime = 3;

    Texture background;
    Date    enteredAt;

    private GameData gameData;

    public LogoState(GameData gameData) {
        this.gameData = gameData;
    }

    public void LoadContent() {
        background = gameData.getTextures().get("Logo");
    }

    public void Draw() {
        float height = gameData.getResolution().getHeight();
        float width  = gameData.getResolution().getWidth();

        gameData.getSpriteBatch().draw(background, 0, 0, width, height);
    }

    public void Update() {

        if (((new Date().getTime() - enteredAt.getTime()) / 1000) > loadTime ||
                Gdx.input.isKeyPressed(Input.Keys.ENTER) ||
                Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {

            gameData.setCurrentState(gameData.getSplashScreenState());
        }
    }

    public void EnteringState() {
        enteredAt = new Date();
    }

    public void ExitingState() {
    }
}
