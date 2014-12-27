package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

/**
 * This state is shown when the player has survived to the end of the game.
 */
public class EndOfGameState implements GameState {
    private GameData gameData;

    public EndOfGameState(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void loadContent() { }

    @Override
    public void draw() {
        Rectangle resolution = gameData.getResolution();

        gameData.getSpriteBatch().draw(
            gameData.getTextures().get("EndOfGame"),
            0,
            0,
            resolution.getWidth(),
            resolution.getHeight()
        );
    }

    @Override
    public void update() {
        boolean isEnterDown  = Gdx.input.isKeyPressed(Keys.ENTER);
        boolean isEscapeDown = Gdx.input.isKeyPressed(Keys.ESCAPE);

        if (isEnterDown || isEscapeDown) {
            gameData.setCurrentState(gameData.getSplashScreenState());
        }
    }

    @Override
    public void enteringState() {
        // TODO: Convert EndOfGameState.EnteringState
        //MediaPlayer.IsRepeating = true;
        //MediaPlayer.Play(gameData.Songs["EndOfGame"]);
    }

    @Override
    public void exitingState() {
        // TODO: Convert EndOfGameState.ExitingState
        //MediaPlayer.Stop();
    }
}
