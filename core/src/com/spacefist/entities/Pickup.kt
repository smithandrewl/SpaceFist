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
 * Represents a pickup item which runs a function when it collides with the players ship.
 *
 * @param gameData Common game data
 * @param texture The image of the pickup
 * @param sound The sound to play when picked up
 * @param position The initial location of the pickup
 * @param velocity The initial velocity of the pickup
 * @param pickupHandler The function to run on pickup
 */
class Pickup(
    gameData:                  GameData,
    texture:                   Texture,
    sound:                     com.badlogic.gdx.audio.Sound,
    position:                  Vector2,
    velocity:                  Vector2,
    private val pickupHandler: (Ship) -> Boolean
) : Entity(
        gameData,
        Rectangle(
            position.x.toInt().toFloat(),
            position.y.toInt().toFloat(),
            texture.width.toFloat(),
            texture.height.toFloat()
        ),
        Physics(),
        NullInputComponent(),
        Sprite(texture),
        Sound(sound),
        gameData.screenScale
) {
    private val pickupSound: Sound

    init {
        pickupSound   = Sound(sound)
        this.velocity = velocity
    }

    /**
     * PickedUp calls pickupHandler when the pickup has been "picked up"
     * @return if the pickup should be removed
     */
    fun pickedUp(ship: Ship): Boolean {
        val pickedUp = pickupHandler(ship)

        if (pickedUp) {
            pickupSound.play()
        }

        return pickedUp
    }
}
