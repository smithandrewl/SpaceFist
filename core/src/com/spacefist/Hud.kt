package com.spacefist

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.spacefist.managers.PlayerManager

/**
 * Draws information about the ongoing game and the player to the screen.
 */
class Hud(private val gameData: GameData, shipManager: PlayerManager) {

    private var scoreDisplay = ""

    private val controlsPosition: Vector2
    private val roundData: RoundData
    private var scorePosition: Vector2? = null
    private val BottomRect: Rectangle
    private val TopRect: Rectangle

    init {
        this.roundData = gameData.roundData

        val resolution = gameData.resolution

        controlsPosition = Vector2(
                resolution.getWidth() * .5f - CONTROLS_MSG.length * 4.5f,
                resolution.getHeight() * .025f
        )

        BottomRect = Rectangle(
                0f,
                0f,
                gameData.textures["Hud"]!!.getWidth().toFloat(),
                gameData.textures["Hud"]!!.getHeight().toFloat()
        )

        TopRect = Rectangle(
                0f,
                gameData.resolution.getHeight() - gameData.textures["Hud"]!!.getHeight(),
                gameData.textures["Hud"]!!.getWidth().toFloat(),
                gameData.textures["Hud"]!!.getHeight().toFloat()
        )
    }

    fun update() {

        scoreDisplay = String.format(
                SCORE_FORMAT,
                roundData.score,
                (gameData.ship.health * 100).toInt(),
                roundData.lives
        )

        scorePosition = Vector2(
                gameData.resolution.getWidth() * .5f - scoreDisplay.length * 5,
                gameData.resolution.getHeight() * .994f
        )

    }

    fun draw() {
        val font = gameData.font

        //draw the top rectangle
        gameData.spriteBatch.draw(gameData.textures["Hud"], TopRect.x, TopRect.y)

        // draw the score
        font.draw(gameData.spriteBatch, scoreDisplay, scorePosition!!.x, scorePosition!!.y)

        // draw the bottom rectangle
        gameData.spriteBatch.draw(gameData.textures["Hud"], BottomRect.x, BottomRect.y)


        // Write the controls message to the screen
        font.draw(gameData.spriteBatch, CONTROLS_MSG, controlsPosition.x, controlsPosition.y)
    }

    companion object {
        private val SCORE_FORMAT = "Score: %d | Health: %d%% | Lives: %d"
        private val CONTROLS_MSG = "Controls: WASD to move, SPACE to fire, Q to quit"
    }
}
