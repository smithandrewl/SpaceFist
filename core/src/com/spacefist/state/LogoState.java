package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

import java.util.Date;

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

        if (((new Date().getTime() - enteredAt.getTime()) / 1000) > LOAD_TIME ||
                Gdx.input.isKeyPressed(Keys.ENTER) ||
                Gdx.input.isKeyPressed(Keys.ESCAPE)) {

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
