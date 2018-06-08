package com.spacefist.ai

import com.badlogic.gdx.utils.TimeUtils
import com.spacefist.GameData
import com.spacefist.RoundData
import com.spacefist.ai.abst.FuzzyLogicEnabled

/**
 * Provides fuzzy information about the ship and how the player
 * has played this current round.
 */
/**
 * Creates a new ShipInfo instance.
 *
 * @param gameData Common game data
 */
class ShipInfo(private val gameData: GameData) : FuzzyLogicEnabled() {
    // The last time debug information was displayed
    private var lastPrint = TimeUtils.millis()


    // ship data
    private var health: Float = 0.toFloat()
    private var speed:  Int   = 0

    // round data
    private val roundData: RoundData = gameData.roundData

    private val fuzzySpeed:        FuzzyVariable
    private val fuzzyTriggerHappy: FuzzyVariable
    private val fuzzyAccuracy:     FuzzyVariable

    private val fuzzyHealth:       FuzzyVariable = FuzzyVariable()

    /**
     * @return The degree to which the player has low, medium or high accuracy.
     */
    val accuracy: FuzzyVariable
        get() = FuzzyLogicEnabled.grade(roundData.acc, ACCURACY_LOW, ACCURACY_HIGH, fuzzyAccuracy)

    init {

        fuzzyHealth.name = "Health"

        fuzzySpeed      = FuzzyVariable()
        fuzzySpeed.name = "Speed"

        fuzzyTriggerHappy      = FuzzyVariable()
        fuzzyTriggerHappy.name = "Trigger Happy"

        fuzzyAccuracy      = FuzzyVariable()
        fuzzyAccuracy.name = "Accuracy"
    }

    /**
     * @return The degree to which the ship belongs to the low, medium and high speed sets.
     */
    fun getSpeed(): FuzzyVariable {
        return FuzzyLogicEnabled.grade(speed.toFloat(), SPEED_LOW, SPEED_HIGH, fuzzySpeed)
    }

    /**
     * @return The degree to which the ship belongs to the low, medium and high health sets.
     */
    fun getHealth(): FuzzyVariable {
        return FuzzyLogicEnabled.grade(health, HEALTH_LOW, HEALTH_HIGH, fuzzyHealth)
    }

    override fun update() {
        val ship = gameData.getShip()

        // The ships speed is the magnitude of its velocity
        speed  = ship.velocity!!.len().toInt()
        health = ship.health

        if (DISPLAY_DEBUG) {
            printDebugInfo()
        }
    }

    /**
     * Displays fuzzy membership information once a second.
     */
    private fun printDebugInfo() {
        val timeSinceLastPrint = TimeUtils.millis() - lastPrint
        val timeToPrintDebug   = (timeSinceLastPrint / 1000) >= 1

        if (timeToPrintDebug) {
            println("Ship Info:")
            println(getSpeed())
            println(getHealth())
            println(accuracy)
            println()

            lastPrint = TimeUtils.millis()
        }
    }

    companion object {
        private val DISPLAY_DEBUG = false

        // ------------------- fuzzy variable range constants ---------
        private val SPEED_HIGH    = 20f
        private val SPEED_LOW     = 0f

        private val ACCURACY_HIGH = 1f
        private val ACCURACY_LOW  = 0f

        private val HEALTH_LOW    = 0f
        private val HEALTH_HIGH   = 100f
    }
}
