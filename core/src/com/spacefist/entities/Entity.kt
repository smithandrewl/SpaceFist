package com.spacefist.entities

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.components.abst.GraphicsComponent
import com.spacefist.components.abst.InputComponent
import com.spacefist.components.abst.PhysicsComponent
import com.spacefist.components.abst.SoundComponent

/**
 * The base class of all entities.  Anything that moves and is drawn in the game is
 * a subclass of the Entity class.
 *
 *
 * Instead of performing tasks such as drawing or handling input,
 * the Entity base class keeps references to classes implementing known interfaces.  It then calls methods of these interfaces
 * such as update and draw.
 *
 *
 * What this means is that the Entity base class can update and draw itself without knowning how it is being updated or drawn.
 *
 *
 * On update, the Entity base class calls the update method on the graphics, physics, input and sound components.
 * On draw, the Entity base class calls the draw method on the graphics component.
 *
 *
 * Each Entity Subclass provides the exact implementations of the components in its constructor.
 *
 *
 * For example, The block class uses Sprite as its GraphicsComponent because it only needs to draw a single image.
 * The Explosion and Ship classes use the IndexedSprite component as its GraphicsComponent because it needs to draw
 * itself in multiple ways (each frame of the explosion or when the ship turns).
 *
 *
 * As much as possible, the Entity class contains data about the entity, while the components operate on the data.
 *
 * @param gameData  Common game data
 * @param rectangle The size and position of the entity
 * @param physics   The physics component to use
 * @param input     The input component to use
 * @param graphics  The graphics component to use
 * @param sound     The sound component to use
 * @param rotation  The rotation of the entity
 */
open class Entity(
        gameData:  GameData,
    var rectangle: Rectangle?,
    var physics:   PhysicsComponent?,
    var input:     InputComponent?,
    var graphics:  GraphicsComponent?,
    var sound:     SoundComponent?,
    rotation:      Float
) {
    var gameData: GameData
        protected set

    /**
     * The speed at which the entity rotates
     */
    var angularVelocity: Int = 0

    /**
     * The direction and speed of the entity
     */
    var velocity: Vector2? = null

    /**
     * Whether the entity is in play or not. The default update and draw methods
     * only update and draw when the entity is Alive.
     */
    var isAlive: Boolean = false

    /**
     * A hue which can be used by a graphics component when drawning the entity.
     */
    var tint: Color? = null
    /**
     * This represents the rotation of the entity.
     */
    var rotation: Float = 0.toFloat()

    /**
     * This property provides an easy way to get and set the X coordinate of the rectangle associated with this entity.
     */
    var x: Int
        get() = rectangle!!.getX().toInt()
        set(x) {
            rectangle!!.setX(x.toFloat())
        }

    /**
     * This property provides an easy way to get and set the Y coordinate of the rectangle associated with this entity.
     */
    var y: Int
        get() = rectangle!!.getY().toInt()
        set(y) {
            rectangle!!.setY(y.toFloat())
        }

    init {
        isAlive = true

        this.gameData = gameData

        tint     = Color.WHITE
        velocity = Vector2.Zero
    }

    fun initialize() {}

    /**
     * If the ship is alive, update all of its components
     */
    open fun update() {
        if (isAlive) {
            graphics!!.update(gameData, this)
            input!!.update(gameData, this)
            physics!!.update(gameData, this)
            sound!!.update(gameData, this)
        }
    }

    /**
     * If the ship is alive, call draw on its graphics component
     */
    fun draw() {
        if (isAlive) {
            graphics!!.draw(gameData, this)
        }
    }
}