package com.spacefist.ai;

import com.spacefist.GameData;
import com.spacefist.RoundData;
import com.spacefist.ai.abst.FuzzyLogicEnabled;

import java.util.Date;

/// <summary>
/// Provides fuzzy information about the ship and how the player
/// has played this current round.
/// </summary>
public class ShipInfo extends FuzzyLogicEnabled {
    private static final boolean DisplayDebug = false;

    // ------------------- fuzzy variable range constants ---------
    private static final float SpeedHigh        = 20;
    private static final float SpeedLow         = 0;

    private static final float TriggerHappyHigh = 60;
    private static final float TriggerHappyLow  = 0;

    private static final float AccuracyHigh     = 1;
    private static final float AccuracyLow      = 0;

    private static final float HealthLow        = 0;
    private static final float HealthHigh       = 100;
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
    private GameData      gameData;
    // ===============================================================

    /// <summary>
    /// The degree to which the ship belongs to the low, medium and high speed sets.
    /// </summary>
    public FuzzyVariable getSpeed() {
        return grade(speed, SpeedLow, SpeedHigh, fuzzySpeed);
    }

    /// <summary>
    /// The degree to which the ship belongs to the low, medium and high health sets.
    /// </summary>
    public FuzzyVariable getHealth() {
        return grade(health, HealthLow, HealthHigh, fuzzyHealth);
    }


    /* TODO: Implement or remove ShipInfo.getTriggerHappy()
    /// <summary>
    /// The degree to which the ship belongs to the low, medium and high sets for firing often.
    /// If the player never shoots, the membership in TriggerHappy.Low will be 100 percent.
    /// </summary>
    public FuzzyVariable getTriggerHappy() {
            return grade(roundData.ShotsPerPeriod, TriggerHappyLow, TriggerHappyHigh, fuzzyTriggerHappy);
    }
    */

    /// <summary>
    /// The degree to which the player has low, medium or high accuracy.
    /// </summary>
    public FuzzyVariable getAccuracy() {
        return grade(roundData.getAcc(), AccuracyLow, AccuracyHigh, fuzzyAccuracy);
    }

    /// <summary>
    /// Creates a new ShipInfo instance.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    public ShipInfo(GameData gameData)
    {
        this.roundData    = gameData.getRoundData();
        this.gameData     = gameData;

        fuzzyHealth = new FuzzyVariable();
        fuzzyHealth.setName("Health");

        fuzzySpeed = new FuzzyVariable();
        fuzzySpeed.setName("Speed");

        fuzzyTriggerHappy = new FuzzyVariable();
        fuzzyTriggerHappy.setName("Trigger Happy");

        fuzzyAccuracy = new FuzzyVariable();
        fuzzyAccuracy.setName("Accuracy");
    }

    @Override
    public void Update()
    {
        // The ships speed is the magnitude of its velocity
        speed  = (int) gameData.getShip().getVelocity().len();
        health = gameData.getShip().getHealth();

        if (DisplayDebug)
        {
            PrintDebugInfo();
        }
    }

    /// <summary>
    /// Displays fuzzy membership information once a second.
    /// </summary>
    private void PrintDebugInfo()
    {
        if (((new Date().getTime() - LastPrint.getTime()) / 1000) >= 1)
        {
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