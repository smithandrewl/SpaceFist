package com.spacefist.state;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

import java.util.Date;

import static com.badlogic.gdx.Gdx.input;

/**
 * This state displays the team logo before switching
 * to the splash screen.
 */
public class LogoState implements GameState {
    private static final int LOAD_TIME = 3;

    Texture background;
    Date    enteredAt;

    private GameData gameData;

    public LogoState(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void loadContent() {
        background = gameData.getTextures().get("Logo");
    }

    @Override
    public void draw() {
        Rectangle resolution = gameData.getResolution();

        float height = resolution.getHeight();
        float width  = resolution.getWidth();

        gameData.getSpriteBatch().draw(background, 0, 0, width, height);
    }

    @Override
    public void update() {

        long secsSinceEntered = (new Date().getTime() - enteredAt.getTime()) / 1000;

        boolean timeLimitReached = secsSinceEntered > LOAD_TIME;
        boolean skipRequested = input.isKeyPressed(Keys.ENTER) || input.isKeyPressed(Keys.ESCAPE);

        boolean switchToSplashScreen = timeLimitReached || skipRequested;

        if (switchToSplashScreen) {
            gameData.setCurrentState(gameData.getSplashScreenState());
        }
    }

    @Override
    public void enteringState() {
        enteredAt = new Date();
    }

    @Override
    public void exitingState() {
    }
}
