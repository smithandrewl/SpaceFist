package com.spacefist.ai.aggressiveai;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.spacefist.GameData;
import com.spacefist.ai.FuzzyVariable;
import com.spacefist.ai.ShipEnemyInfo;
import com.spacefist.ai.ShipInfo;
import com.spacefist.ai.abst.EnemyAI;
import com.spacefist.ai.abst.EnemyAIState;
import com.spacefist.ai.abst.FuzzyLogicEnabled;
import com.spacefist.entities.Ship;
import com.spacefist.entities.enemies.Enemy;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A fuzzy state that rams the players ship.
 *
 * The likelyhood of ramming the players ship and the path the enemy takes are determined
 * by the degree that the state is active.
 */
public class RamState extends FuzzyLogicEnabled implements EnemyAIState {
    private static final int SPEED = 6;

    private EnemyAI       ai;
    private Enemy         enemy;
    private GameData      gameData;
    private long          lastUpdate;

    public RamState(@NotNull EnemyAI ai, GameData gameData) {

        ShipEnemyInfo shipEnemyInfo = ai.getShipEnemyInfo();

        this.ai       = ai;
        enemy         = shipEnemyInfo.getEnemy();
        lastUpdate    = TimeUtils.millis();
        this.gameData = gameData;
    }

    /**
     * Determines whether or not one point is near another (within 10 pixels).
     *
     * @param x1 The X coordinate of point 1
     * @param y1 The Y coordinate of point 1
     * @param x2 The X coordinate of point 2
     * @param y2 The Y coordinate of point 2
     * @return Returns true if the two points are within 10 pixels of each other
     */
    private static boolean isNear(int x1, int y1, int x2, int y2) {
        int tolerance = 10;

        boolean xIsNear = Math.abs(x1 - x2) <= tolerance;
        boolean yIsNear = Math.abs(y1 - y2) <= tolerance;

        return xIsNear && yIsNear;
    }

    /**
     * Updates the degree to which this state is active.
     */
    @Override
    public void update() {
        Ship ship = gameData.getShip();

        ShipInfo      shipInfo      = ai.getShipInfo();
        ShipEnemyInfo shipEnemyInfo = ai.getShipEnemyInfo();

        FuzzyVariable accuracy = shipInfo.getAccuracy();
        FuzzyVariable health   = shipInfo.getHealth();
        FuzzyVariable distance = shipEnemyInfo.getDistance();

        float membership = or(
            // If the player is doing too well
            and(
                accuracy.getHigh(),
                health.getHigh()
            ),
            // If the player is not too far away
            not(distance.getHigh())
        );

        Vector2 shipLocation = new Vector2(
            ship.getX() ,
            ship.getY()
        );

        Vector2 wayPoint = shipLocation;



        // The line of sight vector
        Vector2 direction = wayPoint.sub(
                new Vector2(
                        enemy.getX(),
                        enemy.getY()
                )
        );

        direction = direction.nor();


        //direction = new Vector2(enemy.getX(), enemy.getY()).nor();

        // The rotation of the ship needed for it to face in the direction of the next waypoint
        float destRotation = (direction.angle()+90);

        System.out.println(destRotation);

        enemy.setRotation(destRotation);

        // Calculate a velocity to move along the line of sight at a magnitude of 5
        // TODO: Convert linear interpolation code in RamState
        //enemy.Velocity = (direction * (MathHelper.Lerp(Enemy.Velocity.Length(), SPEED, .15f) * membership));

        
        enemy.setVelocity(new Vector2(direction.x * SPEED * membership, direction.y * SPEED  * membership * -1.0f));
    }


    public EnemyAI getAi() {
        return ai;
    }

    public void setAi(EnemyAI ai) {
        this.ai = ai;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}
