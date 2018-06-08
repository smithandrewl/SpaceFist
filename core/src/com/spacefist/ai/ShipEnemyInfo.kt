package com.spacefist.ai

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.spacefist.GameData
import com.spacefist.ai.abst.FuzzyLogicEnabled
import com.spacefist.entities.enemies.Enemy

/**
 * Provides information about the players ship that is specific to a particular enemy.
 *
 * @param enemy    The enemy this information is relevant to
 * @param shipInfo General ship information
 * @param gameData Common game Data
 */
class ShipEnemyInfo(
                var enemy:    Enemy?,
        private val shipInfo: ShipInfo,
        private val gameData: GameData
) : FuzzyLogicEnabled() {
    private val fuzzyDistance: FuzzyVariable

    private var distance:  Int  = 0
    private var lastPrint: Long = TimeUtils.millis()

    /**
     * @return whether or not the enemy is on the screen
     */
    val isEnemyVisible: Boolean
        get() = if (enemy!!.isAlive) {
            gameData.onScreenWorld.contains(enemy!!.x.toFloat(), enemy!!.y.toFloat())
        } else {
            false
        }

    /**
     * @return A vector from the enemy to the ship representing its line of sight.
     */
    val lineOfSight: Vector2
        get() {
            val ship     = gameData.ship
            val shipX    = ship.x
            val shipY    = ship.y
            val enemyX   = enemy!!.x
            val enemyY   = enemy!!.y

            val shipPos  = Vector2(shipX.toFloat(), shipY.toFloat())
            val enemyPos = Vector2(enemyX.toFloat(), enemyY.toFloat())

            var diff = shipPos.sub(enemyPos).nor()

            diff = Vector2(diff.x, diff.y * -1)

            return diff
        }

    init {
        // The fuzzy distance from the player to the enemy.
        fuzzyDistance      = FuzzyVariable()
        fuzzyDistance.name = "Distance"
    }

    // Distance
    fun getDistance(): FuzzyVariable {
        return FuzzyLogicEnabled.grade(distance.toFloat(), DISTANCE_LOW, DISTANCE_HIGH, fuzzyDistance)
    }

    override fun update() {
        val ship = gameData.ship

        // update distance
        val xDiff = ship.x - enemy!!.x
        val yDiff = ship.y - enemy!!.y

        distance = Math.sqrt((xDiff * xDiff + yDiff * yDiff).toDouble()).toInt()

        if (DISPLAY_DEBUG) {
            printDebuggingInfo()
        }
    }

    /**
     * Displays details of the fuzzy variables to the console every second.
     */
    private fun printDebuggingInfo() {
        val secondsElapsed = (TimeUtils.millis() - lastPrint) / 1000

        if (secondsElapsed >= 1) {
            val distance = getDistance()

            println("ShipEnemyInfo:")
            println(distance)
            println()

            lastPrint = TimeUtils.millis()
        }
    }

    companion object {
        private val DISPLAY_DEBUG = false

        // The range for the distance fuzzy variable
        private val DISTANCE_HIGH = 1000f
        private val DISTANCE_LOW  = 0f
    }
}
