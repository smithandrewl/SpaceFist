package com.spacefist.state

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Rectangle
import com.spacefist.GameData
import com.spacefist.state.abst.GameState

/**
 * This state is shown when the player has survived to the end of the game.
 */
class EndOfGameState(private val gameData: GameData) : GameState {

    override fun loadContent() {}

    override fun draw() {
        val resolution = gameData.resolution

        gameData.spriteBatch.draw(
                gameData.textures["EndOfGame"],
                0f,
                0f,
                resolution.getWidth(),
                resolution.getHeight()
        )
    }

    override fun update() {
        val isEnterDown = Gdx.input.isKeyPressed(Keys.ENTER)
        val isEscapeDown = Gdx.input.isKeyPressed(Keys.ESCAPE)

        if (isEnterDown || isEscapeDown) {
            gameData.currentState = gameData.splashScreenState
        }
    }

    override fun enteringState() {
        // TODO: Convert EndOfGameState.EnteringState
        //MediaPlayer.IsRepeating = true;
        //MediaPlayer.Play(gameData.Songs["EndOfGame"]);
    }

    override fun exitingState() {
        // TODO: Convert EndOfGameState.ExitingState
        //MediaPlayer.Stop();
    }
}
