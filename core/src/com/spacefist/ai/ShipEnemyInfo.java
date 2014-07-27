package com.spacefist.ai;

import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.ai.abst.FuzzyLogicEnabled;
import com.spacefist.entities.enemies.Enemy;

import java.util.Date;

/**
 * Provides information about the players ship that is specific to a particular enemy.
 */
public class ShipEnemyInfo extends FuzzyLogicEnabled {
    private static final boolean DisplayDebug = false;

    // The range for the distance fuzzy variable
    private static final float DistanceHigh = 1000;
    private static final float DistanceLow  = 0;

    //-------------- Crisp input -----------------
    private int distance;
    // ------------- Crisp Input -----------------

    private GameData      gameData;
    private FuzzyVariable fuzzyDistance;

    private Enemy    enemy;
    private ShipInfo shipInfo;

    private Date lastPrint = new Date();

    /**
     * Creates a new ShipEnemyInfo instance.
     *
     * @param enemy The enemy this information is relevant to
     * @param shipInfo General ship information
     * @param gameData Common game Data
     */
    public ShipEnemyInfo(Enemy enemy, ShipInfo shipInfo, GameData gameData) {
        this.enemy    = enemy;
        this.shipInfo = shipInfo;
        this.gameData = gameData;

        // The fuzzy distance from the player to the enemy.
        fuzzyDistance = new FuzzyVariable();
        fuzzyDistance.setName("Distance");
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    // Distance
    public FuzzyVariable getDistance() {
        return grade(distance, DistanceLow, DistanceHigh, fuzzyDistance);
    }

    /**
     * @return whether or not the enemy is on the screen
     */
    public boolean isEnemyVisible() {
        if (enemy.isAlive()) {
            // TODO: Implement gameData.getOnScreenWorld
            //return gameData.OnScreenWorld.Contains(new Point(Enemy.X, Enemy.Y));
            // TODO: CHANGE ME
            return false;
        } else {
            return false;
        }
    }


    /**
     * @return A vector from the enemy to the ship representing its line of sight.
     */
    public Vector2 getLineOfSight() {
        Vector2 shipPos  = new Vector2(gameData.getShip().getX(), gameData.getShip().getY());
        Vector2 enemyPos = new Vector2(enemy.getX(), enemy.getY());

        Vector2 diff = shipPos.sub(enemyPos);

        return diff.nor();
    }


    @Override
    public void Update() {
        // update distance
        int xDiff = gameData.getShip().getX() - enemy.getX();
        int yDiff = gameData.getShip().getY() - enemy.getY();

        distance = (int) Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));

        if (DisplayDebug) {
            PrintDebuggingInfo();
        }
    }

    /**
     * Displays details of the fuzzy variables to the console every second.
     */
    private void PrintDebuggingInfo() {
        if (((new Date().getTime() - lastPrint.getTime()) / 1000) >= 1) {
            System.out.println("Ship/Enemy Info:");
            System.out.println(getDistance());
            System.out.println();

            lastPrint = new Date();
        }
    }
}
