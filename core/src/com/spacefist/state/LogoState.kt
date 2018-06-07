package com.spacefist.state

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.spacefist.GameData
import com.spacefist.state.abst.GameState

import java.util.Date

import com.badlogic.gdx.Gdx.input

/**
 * This state displays the team logo before switching
 * to the splash screen.
 */
class LogoState(private val gameData: GameData) : GameState {

    internal var background: Texture? = null
    internal var enteredAt: Date? = null

    override fun loadContent() {
        background = gameData.textures["Logo"]!!
    }

    override fun draw() {
        val resolution = gameData.resolution

        val height = resolution.getHeight()
        val width = resolution.getWidth()

        gameData.spriteBatch.draw(background, 0f, 0f, width, height)
    }

    override fun update() {

        val secsSinceEntered = (Date().time - enteredAt!!.time) / 1000

        val timeLimitReached = secsSinceEntered > LOAD_TIME
        val skipRequested = input.isKeyPressed(Keys.ENTER) || input.isKeyPressed(Keys.ESCAPE)

        val switchToSplashScreen = timeLimitReached || skipRequested

        if (switchToSplashScreen) {
            gameData.currentState = gameData.splashScreenState
        }
    }

    override fun enteringState() {
        enteredAt = Date()
    }

    override fun exitingState() {}

    companion object {
        private val LOAD_TIME = 3
    }
}
