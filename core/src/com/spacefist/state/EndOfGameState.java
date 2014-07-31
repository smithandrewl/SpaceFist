package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    public void LoadContent() { }

    public void Draw() {
        Rectangle resolution = gameData.getResolution();

        gameData.getSpriteBatch().draw(
            gameData.getTextures().get("EndOfGame"),
            0,
            0,
            resolution.getWidth(),
            resolution.getHeight()
        );
    }

    public void Update() {
        boolean isEnterDown  = Gdx.input.isKeyPressed(Input.Keys.ENTER);
        boolean isEscapeDown = Gdx.input.isKeyPressed(Input.Keys.ESCAPE);

        if (isEnterDown || isEscapeDown) {
            gameData.setCurrentState(gameData.getSplashScreenState());
        }
    }

    public void EnteringState() {
        // TODO: Convert EndOfGameState.EnteringState
        //MediaPlayer.IsRepeating = true;
        //MediaPlayer.Play(gameData.Songs["EndOfGame"]);
    }

    public void ExitingState() {
        // TODO: Convert EndOfGameState.ExitingState
        //MediaPlayer.Stop();
    }
}
