package com.spacefist.ai

import com.badlogic.gdx.utils.TimeUtils
import com.spacefist.GameData
import com.spacefist.RoundData
import com.spacefist.ai.abst.FuzzyLogicEnabled
import com.spacefist.entities.Ship

/**
 * Provides fuzzy information about the ship and how the player
 * has played this current round.
 */
class ShipInfo
/**
 * Creates a new ShipInfo instance.
 *
 * @param gameData Common game data
 */
(
        // ===============================================================

        private val gameData: GameData) : FuzzyLogicEnabled() {
    // ------------------------------------------------------------

    // The last time debug information was displayed
    private var lastPrint = TimeUtils.millis()

    // ======================== Crisp Input  ========================
    // ship data
    private var health: Float = 0.toFloat()
    private var speed: Int = 0

    // round data
    private val roundData: RoundData
    // ===============================================================

    // ======================== Fuzzy Input Variables ================
    private val fuzzySpeed: FuzzyVariable
    private val fuzzyHealth: FuzzyVariable
    private val fuzzyTriggerHappy: FuzzyVariable
    private val fuzzyAccuracy: FuzzyVariable

    /**
     * @return The degree to which the player has low, medium or high accuracy.
     */
    val accuracy: FuzzyVariable
        get() = FuzzyLogicEnabled.grade(roundData.acc, ACCURACY_LOW, ACCURACY_HIGH, fuzzyAccuracy)

    init {
        roundData = gameData.roundData

        fuzzyHealth = FuzzyVariable()
        fuzzyHealth.name = "Health"

        fuzzySpeed = FuzzyVariable()
        fuzzySpeed.name = "Speed"

        fuzzyTriggerHappy = FuzzyVariable()
        fuzzyTriggerHappy.name = "Trigger Happy"

        fuzzyAccuracy = FuzzyVariable()
        fuzzyAccuracy.name = "Accuracy"
    }

    /**
     * @return The degree to which the ship belongs to the low, medium and high speed sets.
     */
    fun getSpeed(): FuzzyVariable {
        return FuzzyLogicEnabled.grade(speed.toFloat(), SPEED_LOW, SPEED_HIGH, fuzzySpeed)
    }

/* TODO: Implement or remove ShipInfo.getTriggerHappy()
    /// <summary>
    /// The degree to which the ship belongs to the low, medium and high sets for firing often.
    /// If the player never shoots, the membership in TriggerHappy.Low will be 100 percent.
    /// </summary>
    public FuzzyVariable getTriggerHappy() {
            return grade(roundData.ShotsPerPeriod, TRIGGER_HAPPY_LOW, TRIGGER_HAPPY_HIGH, fuzzyTriggerHappy);
>>>>>>> ConvertedCodeCleanup
    }
    */

    /**
     * @return The degree to which the ship belongs to the low, medium and high health sets.
     */
     fun getHealth():FuzzyVariable {
return FuzzyLogicEnabled.grade(health, HEALTH_LOW, HEALTH_HIGH, fuzzyHealth)
}

public override fun update() {
val ship = gameData.getShip()

 // The ships speed is the magnitude of its velocity
        speed = ship.getVelocity().len().toInt()
health = ship.getHealth()

if (DISPLAY_DEBUG)
{
printDebugInfo()
}
}

/**
 * Displays fuzzy membership information once a second.
 */
    private fun printDebugInfo() {
val timeSinceLastPrint = TimeUtils.millis() - lastPrint
val timeToPrintDebug = (timeSinceLastPrint / 1000) >= 1

if (timeToPrintDebug)
{
println("Ship Info:")
println(getSpeed())
println(getHealth())
 //System.out.println(getTriggerHappy());
            println(accuracy)
println()

lastPrint = TimeUtils.millis()
}
}

companion object {
private val DISPLAY_DEBUG = false

 // ------------------- fuzzy variable range constants ---------
    private val SPEED_HIGH = 20f
private val SPEED_LOW = 0f

private val TRIGGER_HAPPY_HIGH = 60f
private val TRIGGER_HAPPY_LOW = 0f

private val ACCURACY_HIGH = 1f
private val ACCURACY_LOW = 0f

private val HEALTH_LOW = 0f
private val HEALTH_HIGH = 100f
}
}
