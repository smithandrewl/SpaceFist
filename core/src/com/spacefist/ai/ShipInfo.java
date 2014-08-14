package com.spacefist.ai;

import com.spacefist.GameData;
import com.spacefist.RoundData;
import com.spacefist.ai.abst.FuzzyLogicEnabled;
import com.spacefist.entities.Ship;

import java.util.Date;

/// <summary>
/// Provides fuzzy information about the ship and how the player
/// has played this current round.
/// </summary>
public class ShipInfo extends FuzzyLogicEnabled {
    private static final boolean DISPLAY_DEBUG = false;

    // ------------------- fuzzy variable range constants ---------
    private static final float SPEED_HIGH = 20;
    private static final float SPEED_LOW  = 0;

    private static final float TRIGGER_HAPPY_HIGH = 60;
    private static final float TRIGGER_HAPPY_LOW  = 0;

    private static final float ACCURACY_HIGH = 1;
    private static final float ACCURACY_LOW  = 0;

    private static final float HEALTH_LOW  = 0;
    private static final float HEALTH_HIGH = 100;
    // ------------------------------------------------------------

    // The last time debug information was displayed
    private Date LastPrint = new Date();

    // ======================== Crisp Input  ========================
    // ship data
    private float health;
    private int   speed;

    // round data
    private RoundData roundData;
    // ===============================================================

    // ======================== Fuzzy Input Variables ================
    private FuzzyVariable fuzzySpeed;
    private FuzzyVariable fuzzyHealth;
    private FuzzyVariable fuzzyTriggerHappy;
    private FuzzyVariable fuzzyAccuracy;
    // ===============================================================

    private GameData gameData;

    /**
     * Creates a new ShipInfo instance.
     *
     * @param gameData Common game data
     */
    public ShipInfo(GameData gameData) {
        roundData     = gameData.getRoundData();
        this.gameData = gameData;

        fuzzyHealth = new FuzzyVariable();
        fuzzyHealth.setName("Health");

        fuzzySpeed = new FuzzyVariable();
        fuzzySpeed.setName("Speed");

        fuzzyTriggerHappy = new FuzzyVariable();
        fuzzyTriggerHappy.setName("Trigger Happy");

        fuzzyAccuracy = new FuzzyVariable();
        fuzzyAccuracy.setName("Accuracy");
    }

    /**
     * @return The degree to which the ship belongs to the low, medium and high speed sets.
     */
    public FuzzyVariable getSpeed() {
        return grade(speed, SPEED_LOW, SPEED_HIGH, fuzzySpeed);
    }

    /* TODO: Implement or remove ShipInfo.getTriggerHappy()
    /// <summary>
    /// The degree to which the ship belongs to the low, medium and high sets for firing often.
    /// If the player never shoots, the membership in TriggerHappy.Low will be 100 percent.
    /// </summary>
    public FuzzyVariable getTriggerHappy() {
            return grade(roundData.ShotsPerPeriod, TRIGGER_HAPPY_LOW, TRIGGER_HAPPY_HIGH, fuzzyTriggerHappy);
    }
    */

    /**
     * @return The degree to which the ship belongs to the low, medium and high health sets.
     */
    public FuzzyVariable getHealth() {
        return grade(health, HEALTH_LOW, HEALTH_HIGH, fuzzyHealth);
    }

    /**
     * @return The degree to which the player has low, medium or high accuracy.
     */
    public FuzzyVariable getAccuracy() {
        return grade(roundData.getAcc(), ACCURACY_LOW, ACCURACY_HIGH, fuzzyAccuracy);
    }

    @Override
    public void update() {
        Ship ship = gameData.getShip();

        // The ships speed is the magnitude of its velocity
        speed  = (int) ship.getVelocity().len();
        health = ship.getHealth();

        if (DISPLAY_DEBUG) {
            PrintDebugInfo();
        }
    }

    /**
     * Displays fuzzy membership information once a second.
     */
    private void PrintDebugInfo() {
        if (((new Date().getTime() - LastPrint.getTime()) / 1000) >= 1) {
            System.out.println("Ship Info:");
            System.out.println(getSpeed());
            System.out.println(getHealth());
            //System.out.println(getTriggerHappy());
            System.out.println(getAccuracy());
            System.out.println();

            LastPrint = new Date();
        }
    }
}
