package com.spacefist.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.components.NullInputComponent
import com.spacefist.components.Physics
import com.spacefist.components.Sound
import com.spacefist.components.Sprite

/**
 * Represents a floating block or crate.
 *
 * @param gameData Common game data
 * @param texture  The image for the block
 * @param position The position of the block in the game world
 * @param velocity The velocity of the block
 */
class SpaceBlock(
    gameData: GameData,
    texture:  Texture,
    position: Vector2,
    velocity: Vector2
) : Entity(
    gameData,
    Rectangle(
        position.x.toInt().toFloat(),
        position.y.toInt().toFloat(),
        gameData.textures["Block"]!!.width.toFloat(),
        gameData.textures["Block"]!!.height.toFloat()
    ),
    Physics(),
    NullInputComponent(),
    Sprite(texture),
    Sound(gameData.soundEffects["Explosion"]!!),
    gameData.screenScale
) {

    // The sound made when the ship hits a block
    private val thump: Sound

    init {
        thump = Sound(gameData.soundEffects["Explosion"]!!)

        this.velocity = velocity
    }

    /**
     * Called when the block has been shot
     */
    fun destroy() {
        val sound = sound as Sound?

        sound!!.play()
        isAlive = false
    }

    /**
     * Called when the block has been bumped by the ship
     */
    fun thump() {
        thump.play()
        isAlive = false
    }

    companion object {
        // Dimensions of the block
        private val WIDTH  = 80
        private val HEIGHT = 60
    }
}
