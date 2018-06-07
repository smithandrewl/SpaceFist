package com.spacefist.managers

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.RoundData
import com.spacefist.entities.Ship
import com.spacefist.state.shipstates.SpawningState
import com.spacefist.weapons.LaserWeapon

/// <summary>
/// Handles player and ship interaction with the game world.
/// </summary>
class PlayerManager/// <summary>
/// Creates a new PlayerManager instance.
/// </summary>
/// <param name="gameData">Common game data</param>
(internal var gameData: GameData) {
    internal var roundData: RoundData

    // Start the players ship moving at a velocity greater than the camer scrolls
    private val StartingVelocity = Vector2(0f, -2f)

    val isAlive: Boolean
        get() = gameData.ship.isAlive

    init {
        roundData = gameData.roundData
    }

    fun initialize() {
        gameData.ship.initialize()
    }

    fun update() {
        if (gameData.ship.isAlive) {
            gameData.ship.update()
        }
    }

    fun draw() {
        if (gameData.ship.isAlive) {
            gameData.ship.draw()
        }
    }

    fun spawn(): Ship {
        gameData.soundEffects["PlayerSpawn"]!!.play()

        val resolution = gameData.resolution
        val camera = gameData.camera

        // Start the ship at the bottom  in the center of the screen

        val xOffset = resolution.getWidth() / 2
        val yOffset = resolution.getHeight() * .05

        val startX = (xOffset + camera.x).toInt()
        val startY = (yOffset + camera.y).toInt()

        if (gameData.ship != null) {
            gameData.ship.setCurrentState(SpawningState(gameData))

            gameData.ship.x = startX
            gameData.ship.y = startY
            gameData.ship.isAlive = true
        } else {
            gameData.ship = Ship(gameData, Vector2(startX.toFloat(), startY.toFloat()))
            gameData.ship.currentState!!.enteringState()
        }

        gameData.ship.healthPoints = 100
        gameData.ship.velocity = StartingVelocity

        return gameData.ship
    }

    fun handleDeath() {
        gameData.ship.onDeath()

        if (roundData.lives > 0) {
            roundData.lives = roundData.lives - 1
            gameData.ship.healthPoints = 100

            spawn()
        } else {
            gameData.ship.isAlive = false
        }

        gameData.ship.weapon = LaserWeapon(gameData)
    }

    fun shipHit() {
        val healthPoints = gameData.ship.healthPoints
        gameData.ship.healthPoints = healthPoints - HIT_DAMAGE

        if (gameData.ship.healthPoints <= 0) {
            handleDeath()
        }
    }

    fun scored() {
        roundData.score = roundData.score + 10
    }

    fun resetScore() {
        roundData.score = 0
    }

    fun resetLives() {
        roundData.lives = 2
    }

    // Replaces the ships current weapon
    // with the laser weapon.
    fun resetWeapon() {
        gameData.ship.weapon = LaserWeapon(gameData)
    }

    companion object {

        // Damage the ship takes per hit
        private val HIT_DAMAGE = 5
    }
}
