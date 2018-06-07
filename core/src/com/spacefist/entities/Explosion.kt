package com.spacefist.entities

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.components.IndexedSprite
import com.spacefist.components.NullInputComponent
import com.spacefist.components.NullSoundComponent
import com.spacefist.components.Physics

/**
 * Represents and draws an explosion at its x and y coordinates.
 *
 * The Explosion instance is "killed" after the animation finishes.
 *
 * @param gameData Common game data
 * @param position The on world location of the explosion
 */
class Explosion(
    gameData: GameData,
    position: Vector2
) : Entity(
    gameData,
    Rectangle(
        position.x.toInt().toFloat(),
        position.y.toInt().toFloat(),
        WIDTH.toFloat(),
        HEIGHT.toFloat()
    ),
    Physics(),
    NullInputComponent(),
    IndexedSprite(
        gameData.textures["Explosion"]!!,
        WIDTH,
        HEIGHT,
        0
    ),
    NullSoundComponent(),
    gameData.screenScale
) {
    // The time the explosion animation started
    // and the time to wait before drawing the next frame
    private var startTime:         Long = 0
    private val TimeBetweenFrames: Long = 20

    init {
        startTime = System.currentTimeMillis()
        velocity  = Vector2(0f, 0f)
    }

    override fun update() {
        val indexedSprite = graphics as IndexedSprite?

        // If the animation is not finished, wait TimeBetweenFrames
        // before switching to the next image of the animation.
        if (indexedSprite!!.index <= LAST_FRAME) {
            val curTime = System.currentTimeMillis()

            if (curTime - startTime >= TimeBetweenFrames) {
                // Tell the IndexedSprite component that it should be drawing the next frame
                indexedSprite.index = indexedSprite.index + 1
                startTime = curTime // reset the time between frames
            }
        } else if (isAlive) {
            isAlive = false
        } // If the animation is over mark the object as dead to (keep it from being updated and drawn)

        super.update()
    }

    companion object {
        // The dimensions of the explosion
        private val HEIGHT = 122
        private val WIDTH  = 122

        // The number of frames / pictures in the animation
        private val LAST_FRAME = 9
    }
}