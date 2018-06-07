package com.spacefist.state

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.spacefist.GameData
import com.spacefist.state.abst.GameState

/**
 * GameOverState draws the game over message until the player
 * hits enter.  When the player hits enter, the player is taken to to the
 * main menu.
 */
class GameOverState(private val gameData: GameData) : GameState {

    override fun loadContent() {}

    override fun enteringState() {
        val song = gameData.songs["GameOver"]

        song!!.setLooping(true)
        song!!.play()
    }

    override fun update() {
        if (Gdx.input.isKeyPressed(Keys.ENTER) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            gameData.currentState = gameData.splashScreenState
        }
    }

    override fun draw() {
        val gameOver = gameData.textures["GameOver"]
        val resolution = gameData.resolution

        val width = resolution.getWidth()
        val height = resolution.getHeight()

        // draw the game over image
        gameData.spriteBatch.draw(gameOver, 0f, 0f, width, height)
    }

    override fun exitingState() {
        val song = gameData.songs["GameOver"]

        song!!.stop()
    }
}
