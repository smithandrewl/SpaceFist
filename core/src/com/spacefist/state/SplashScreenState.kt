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
    internal var overlayTexture: Texture? = null
    internal var overlayRect: Rectangle? = null
    internal var enteredAt: Date? = null

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

    override fun update() {
        val timeDiff = Date().time - enteredAt!!.time

        if (timeDiff / 1000 > 3) {
            gameData.currentState = gameData.menuState
        } else if (timeDiff > 300) {
            if (Gdx.input.isKeyPressed(Keys.ENTER) ||
                    Gdx.input.isKeyPressed(Keys.SPACE) ||
                    Gdx.input.isKeyPressed(Keys.ESCAPE)) {
                gameData.currentState = gameData.menuState
            }

            if (Gdx.input.isTouched) {
                gameData.currentState = gameData.menuState
            }
        }// This waits 300 milliseconds after the splash screen state has been entered
        // before processing input.
    }

    override fun draw() {
        val resolution = gameData.resolution
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
