package com.spacefist.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.spacefist.GameData;
import com.spacefist.ai.abst.FuzzyLogicEnabled;
import com.spacefist.entities.Ship;
import com.spacefist.entities.enemies.Enemy;
import org.jetbrains.annotations.NotNull;

/**
 * Provides information about the players ship that is specific to a particular enemy.
 */
public class ShipEnemyInfo extends FuzzyLogicEnabled {
    private static final boolean DISPLAY_DEBUG = false;

    // The range for the distance fuzzy variable
    private static final float DISTANCE_HIGH = 1000;
    private static final float DISTANCE_LOW  = 0;

    //-------------- Crisp input -----------------
    private int distance;
    // ------------- Crisp Input -----------------

    private GameData      gameData;
    private FuzzyVariable fuzzyDistance;

    private Enemy    enemy;
    private ShipInfo shipInfo;

    private long lastPrint = TimeUtils.millis();

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
    @NotNull
    public FuzzyVariable getDistance() {
        return grade(distance, DISTANCE_LOW, DISTANCE_HIGH, fuzzyDistance);
    }

    /**
     * @return whether or not the enemy is on the screen
     */
    public boolean isEnemyVisible() {
        if (enemy.isAlive()) {
            return gameData.getOnScreenWorld().contains(enemy.getX(), enemy.getY());
        } else {
            return false;
        }
    }


    /**
     * @return A vector from the enemy to the ship representing its line of sight.
     */
    @NotNull
    public Vector2 getLineOfSight() {
        Ship ship  = gameData.getShip();
        int shipX  = ship.getX();
        int shipY  = ship.getY();
        int enemyX = enemy.getX();
        int enemyY = enemy.getY();

        Vector2 shipPos  = new Vector2(shipX, shipY);
        Vector2 enemyPos = new Vector2(enemyX, enemyY);

        Vector2 diff = shipPos.sub(enemyPos);

        return diff.nor();
    }

    @Override
    public void update() {
        Ship ship = gameData.getShip();

        // update distance
        int xDiff = ship.getX() - enemy.getX();
        int yDiff = ship.getY() - enemy.getY();

        distance = (int) Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));

        if (DISPLAY_DEBUG) {
            printDebuggingInfo();
        }
    }

    /**
     * Displays details of the fuzzy variables to the console every second.
     */
    private void printDebuggingInfo() {
        long secondsElapsed = (TimeUtils.millis() - lastPrint) / 1000;

        if (secondsElapsed >= 1) {
            FuzzyVariable distance = getDistance();

            System.out.println("ShipEnemyInfo:");
            System.out.println(distance);
            System.out.println();

            lastPrint = TimeUtils.millis();
        }
    }
}
