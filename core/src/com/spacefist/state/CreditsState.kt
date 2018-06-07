package com.spacefist.state

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.utils.TimeUtils
import com.spacefist.GameData
import com.spacefist.state.abst.GameState

/**
 * This state displays information about the developers of
 * the game.
 */
class CreditsState(private val gameData: GameData) : GameState {
    private var enteredAt: Long = 0

    override fun loadContent() {}

    override fun draw() {
        val resolution = gameData.resolution

        val y = (resolution.getHeight() - 0.63 * resolution.getHeight()).toInt()
        val leftX = (0.1 * resolution.getWidth()).toInt()
        val rightX = (0.7325 * resolution.getWidth()).toInt()
        val centerX = (0.5 * resolution.getWidth()).toInt() - 103

        gameData.spriteBatch.draw(
                gameData.textures["Credits"],
                0f,
                0f,
                resolution.getWidth(),
                resolution.getHeight()
        )

        val font = gameData.font
        val spriteBatch = gameData.spriteBatch

        font.draw(spriteBatch, "Dongcai Huang", leftX.toFloat(), y.toFloat())
        font.draw(spriteBatch, "Programming", leftX.toFloat(), (y - 30).toFloat())
        font.draw(spriteBatch, "Art Selection", leftX.toFloat(), (y - 45).toFloat())

        font.draw(spriteBatch, "Tatsuya Takahashi", rightX.toFloat(), y.toFloat())
        font.draw(spriteBatch, "Programming", (rightX + 40).toFloat(), (y - 30).toFloat())

        font.draw(spriteBatch, "Andrew Smith", centerX.toFloat(), y.toFloat())
        font.draw(spriteBatch, "Programming / AI", (centerX - 30).toFloat(), (y - 30).toFloat())
        font.draw(spriteBatch, "Art Selection", centerX.toFloat(), (y - 45).toFloat())
        font.draw(spriteBatch, "Sound Selection", (centerX - 10).toFloat(), (y - 60).toFloat())
    }

    override fun update() {

        val timeSinceEnter = TimeUtils.millis() - enteredAt
        val safeToProcessInput = timeSinceEnter > 300

        if (safeToProcessInput) {
            enteredAt = TimeUtils.millis()

            val mousePressed = Gdx.input.isTouched
            val isEnterDown = Gdx.input.isKeyPressed(Keys.ENTER)
            val isEscapeDown = Gdx.input.isKeyPressed(Keys.ESCAPE)

            if (mousePressed || isEnterDown || isEscapeDown) {
                gameData.currentState = gameData.menuState
            }
        }

    }

    override fun enteringState() {
        enteredAt = TimeUtils.millis()
    }

    override fun exitingState() {}
}
