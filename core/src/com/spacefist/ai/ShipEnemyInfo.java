package com.spacefist.ai;

import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.ai.abst.FuzzyLogicEnabled;
import com.spacefist.entities.enemies.Enemy;

import java.util.Date;

/// <summary>
/// Provides information about the players ship that is specific to a particular enemy.
/// </summary>
public class ShipEnemyInfo extends FuzzyLogicEnabled
{
    private static final boolean DisplayDebug = false;

    // The range for the distance fuzzy variable
    private static final float DistanceHigh = 1000;
    private static final float DistanceLow  = 0;

    //-------------- Crisp input -----------------
    private int distance;
    // ------------- Crisp Input -----------------

    private GameData gameData;
    private FuzzyVariable fuzzyDistance;

    private Enemy    enemy;
    private ShipInfo shipInfo;

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    private Date lastPrint = new Date();

    /// <summary>
    /// Creates a new ShipEnemyInfo instance.
    /// </summary>
    /// <param name="enemy">The enemy this information is relevant to</param>
    /// <param name="shipInfo">General ship information</param>
    /// <param name="gameData">Common game Data</param>
    public ShipEnemyInfo(Enemy enemy, ShipInfo shipInfo, GameData gameData)
    {
        this.enemy    = enemy;
        this.shipInfo = shipInfo;
        this.gameData = gameData;

        /// <summary>
        /// The fuzzy distance from the player to the enemy.
        /// </summary>
        fuzzyDistance = new FuzzyVariable();
        fuzzyDistance.setName("Distance");
    }

    // Distance
    public FuzzyVariable getDistance()
    {
        return grade(distance, DistanceLow, DistanceHigh, fuzzyDistance);
    }

    /// <summary>
    /// True if the enemy is on the screen.
    /// </summary>
    public boolean isEnemyVisible()
    {
        if (enemy.isAlive())
        {
            // TODO: Implement gameData.getOnScreenWorld
            //return gameData.OnScreenWorld.Contains(new Point(Enemy.X, Enemy.Y));
            // TODO: CHANGE ME
            return false;
        }
        else
        {
            return false;
        }
    }


    /// <summary>
    /// A vector from the enemy to the ship representing its line of sight.
    /// </summary>
    public Vector2 getLineOfSight() {
        Vector2 shipPos  = new Vector2(gameData.getShip().getX(), gameData.getShip().getY());
        Vector2 enemyPos = new Vector2(enemy.getX(), enemy.getY());

        Vector2 diff = shipPos.sub(enemyPos);
        diff = diff.nor();

        return diff;
    }


    @Override
    public void Update()
    {
        // update distance
        int xDiff = gameData.getShip().getX() - enemy.getX();
        int yDiff = gameData.getShip().getY() - enemy.getY();

        distance = (int) Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));

        if (DisplayDebug)
        {
            PrintDebuggingInfo();
        }
    }

    /// <summary>
    /// Displays details of the fuzzy variables to the console every second.
    /// </summary>
    private void PrintDebuggingInfo()
    {
        if (((new Date().getTime() - lastPrint.getTime()) / 1000 ) >= 1)
        {
            System.out.println("Ship/Enemy Info:");
            System.out.println(getDistance());
            System.out.println();

            lastPrint = new Date();
        }
    }
}

