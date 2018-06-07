package com.spacefist.entities

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.components.IndexedSprite
import com.spacefist.components.Physics
import com.spacefist.components.ShipInput
import com.spacefist.components.Sound
import com.spacefist.state.abst.ShipState
import com.spacefist.state.abst.StateMachine
import com.spacefist.state.shipstates.NormalState
import com.spacefist.state.shipstates.SpawningState
import com.spacefist.weapons.LaserWeapon
import com.spacefist.weapons.abst.Weapon

import com.badlogic.gdx.math.MathUtils.clamp

/**
 * Creates a new Ship instance at the specified location.
 *
 * @param gameData Common game data
 * @param position The location of the ship in the game world
 */
class Ship(
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
        ShipInput(),
        IndexedSprite(
            gameData.textures["ShipSheet"]!!,
            WIDTH,
            HEIGHT,
            4
        ),
        Sound(gameData.soundEffects["PlayerDeath"]!!),
        gameData.screenScale
), StateMachine<ShipState> {

    /**
     * The current behavior of the ship
     *
     * The current states are spawning, normal and low health
     */
    private var state: ShipState? = null
    /**
     * @return The ships current weapon.
     */
    var weapon: Weapon? = null

    /**
     * The number of health points the ship has left.
     */
    var healthPoints: Int = 0

    private val indexedSprite: IndexedSprite

    // The predefined velocities to use when changing position
    private val LeftVelocity     = Vector2(-1f, 0f)
    private val RightVelocity    = Vector2(1f,  0f)
    private val ForwardVelocity  = Vector2(0f,  -1f)
    private val BackwardVelocity = Vector2(0f,  1f)

    val health: Float
        get() = healthPoints / MAX_HEALTH_POINTS.toFloat()

    init {
        indexedSprite = graphics as IndexedSprite

        healthPoints = 100
        weapon = LaserWeapon(gameData)

        // Start the ship in the spawning state
        state = SpawningState(gameData)
    }

    override fun getCurrentState(): ShipState? {
        return state
    }

    override fun setCurrentState(shipState: ShipState) {
        state!!.exitingState()
        shipState.enteringState()
        state = shipState
    }

    /**
     * Plays the ships destruction sound effect.
     */
    fun onDeath() {
        (sound as Sound).play()
    }

    /**
     * Resets the ship to the default state (drawn normally (no flashing or fading)).
     */
    fun resetState() {
        setCurrentState(NormalState(gameData))
        reset()
    }

    /**
     * Updates the ship
     */
    override fun update() {
        state!!.update()
        super.update()
    }

    /**
     * Fire the active weapon
     */
    fun fire() {
        weapon!!.fire()
    }

    fun reset() {
        // This causes the ship to be drawn in its default state (not turning left or right)
        indexedSprite.index = AT_REST_INDEX
    }

    /**
     * Changes the velocity by "velocity" and then clamps the value between the minimum and maximum velocities
     * allowed.
     */
    private fun incrementVelocity(velocity: Vector2) {
        val xVel = clamp(
                velocity.x + velocity.x,
                (-MAX_VELOCITY).toFloat(),
                MAX_VELOCITY.toFloat()
        )

        val yVel = clamp(
                velocity.y + velocity.y,
                (-MAX_VELOCITY).toFloat(),
                MAX_VELOCITY.toFloat()
        )

        this.velocity = Vector2(xVel, yVel)
    }

    /**
     * Causes the ship to move to the left.
     */
    fun left() {
        // This tells indexedSprite to draw the ship turning left
        indexedSprite.index = LEFT_INDEX

        // Change to a left moving velocity
        incrementVelocity(LeftVelocity)
    }

    /**
     * Causes the ship to move to the right.
     */
    fun right() {
        // This tells indexedSprite to draw the ship turning right
        indexedSprite.index = RIGHT_INDEX

        // Change to a right moving velocity
        incrementVelocity(RightVelocity)
    }

    /**
     * Causes the ship to move forward
     */
    fun forward() {
        // This tells indexedSprite to draw the ship normally (not turning)
        indexedSprite.index = AT_REST_INDEX

        // Change to a forward moving velocity
        incrementVelocity(ForwardVelocity)
    }

    fun backward() {
        // This tells indexedSprite to draw the ship normally (not turning)
        indexedSprite.index = AT_REST_INDEX

        // Change to a backwards moving velocity
        incrementVelocity(BackwardVelocity)
    }

    companion object {
        private val MAX_HEALTH_POINTS = 100

        // The dimensions of the ship
        private val WIDTH  = 60
        private val HEIGHT = 133

        // The frame of the sprite sheet (ShipSheet.png) to draw
        // Frame 0 is the ship turning left
        // Frame 4 is the ship in its normal state
        // Frame 7 is the ship turning right
        private val LEFT_INDEX    = 0
        private val AT_REST_INDEX = 4
        private val RIGHT_INDEX   = 7
        private val MAX_VELOCITY  = 20
    }
}
