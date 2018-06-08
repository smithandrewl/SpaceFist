package com.spacefist.state

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.spacefist.GameData
import com.spacefist.state.abst.GameState
import java.util.*

//FIXME: The splash-screen image is drawn skewed
/**
 * When the game is in the splash screen state
 * it draws the splash screen and waits for the player to press enter or click the mouse.
 *
 * When the player presses enter the game switchs to the menu state.
 */
class SplashScreenState
/**
 * Creates a new SplashScreenState instance.
 *
 * @param gameData Common game data
 */
(internal var gameData: GameData) : GameState {
    internal var overlayTexture: Texture?   = null
    internal var overlayRect:    Rectangle? = null
    internal var enteredAt:      Date?      = null

    override fun loadContent() {
        val resolution = gameData.resolution

        overlayTexture = gameData.textures["TitleScreen"]

        overlayRect = Rectangle(
                0f,
                0f,
                resolution.getWidth(),
                resolution.getHeight()
        )
    }

    override fun enteringState() {
        enteredAt = Date()
    }

    private fun userWantsToSkipScreen(): Boolean {
        val skipSplashScreen =
                Gdx.input.isKeyPressed(Keys.ENTER) ||
                Gdx.input.isKeyPressed(Keys.SPACE) ||
                Gdx.input.isKeyPressed(Keys.ESCAPE)||
                Gdx.input.isTouched

        return skipSplashScreen
    }


    private fun timeToSkipScreen(): Boolean {
        val timeDiff       = Date().time - enteredAt!!.time
        val secondsElapsed = timeDiff / 1000

        return secondsElapsed > 3
    }

    override fun update() {
        val timeDiff = Date().time - enteredAt!!.time

        // This waits 300 milliseconds after the splash screen state has been entered
        // before processing input.
        val safeToCheckInput = timeDiff > 300

        // If it is time to skip, or the user has requested it, skip to the menu screen
        if (timeToSkipScreen()|| (safeToCheckInput && userWantsToSkipScreen())) {
            gameData.currentState = gameData.menuState
        }
    }

    override fun draw() {
        val resolution  = gameData.resolution
        val spriteBatch = gameData.spriteBatch

        spriteBatch.draw(
                gameData.textures["Background"],
                0f,
                0f,
                resolution.getWidth(),
                resolution.getHeight()
        )

        spriteBatch.draw(
                overlayTexture,
                0f,
                0f,
                overlayRect!!.getWidth(),
                overlayRect!!.getHeight()
        )
    }

    override fun exitingState() {}
}
